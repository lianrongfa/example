package corss.util;

import com.alibaba.fastjson.JSONObject;
import corss.controller.Controller;
import corss.proxy.SimpleFactory;
import corss.server.netty.protocol.ProtocolFactory;
import corss.server.netty.protocol.UART;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lianrongfa
 * @date 2018/8/29
 */
public class PushToWeb implements Runnable{

    private final static Logger logger= LoggerFactory.getLogger(PushToWeb.class);

    private ExecutorService executorService= Executors.newCachedThreadPool();

    private AtomicInteger ctl =new AtomicInteger(0);

    public final ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();

    public void executor(){
        scheduledThreadPool.scheduleWithFixedDelay(this,10000,1000, TimeUnit.MILLISECONDS);
    }



    @Override
    public void run() {
        int  timeout=3;
        Jedis jedis = RedisUtils.getJedis();
        out:while (true){
            try {
                List<String> cross =jedis.blpop(timeout, "cross");
                if (cross!=null&&cross.size() > 0) {
                    String json = cross.get(1);
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
                    Byte mark = jsonObject.getByte("mark");

                    Class byMark = ProtocolFactory.getClassByMark(mark);

                    UART uart = (UART) JSONObject.parseObject(json, byMark);

                    executorService.execute(new PushTask(uart,json));

                }else{
                    System.out.println(Thread.currentThread().getName()+"暂时没有数据！");
                    jedis.close();
                    break out;
                }
            }catch (Exception e){
                e.printStackTrace();;
            }
        }

    }

    static class PushTask implements Runnable{

        private UART uart;

        private Jedis jedis=RedisUtils.getJedis();

        private String json;

        public PushTask(UART uart,String json) {
            this.uart = uart;
            this.json=json;
        }

        @Override
        public void run() {
            Controller controller = SimpleFactory.createController(this.uart);
            if (controller != null) {
                try {
                    controller.executor();
                } catch (Exception e) {
                    InvocationTargetException targetException = (InvocationTargetException) e;
                    Throwable throwable = targetException.getTargetException();
                    if (throwable instanceof SocketTimeoutException) {
                        this.jedis.lpush("cross", this.json);
                    }
                } finally {
                    this.jedis.close();
                }

            } else {
                logger.warn("未找到对应的控制器!");
            }
        }
    }
}

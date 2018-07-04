package corss.util;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyContainer;
import corss.ui.ChannelState;
import corss.ui.Monitor;
import corss.ui.SortVector;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lianrongfa
 * @date 2018/7/3
 */
public class UiTimedTask implements Runnable{
    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    private volatile Vector<SortVector<String>> vector=new Vector<SortVector<String>>();

    @Override
    public void run() {
        Monitor monitor = ConfigContext.getInstace().getMonitor();

        //已注册通道数量
        int registerNum = NettyContainer.sourceChannels.size();
        //未注册通道数量
        int uncheckedNum= NettyContainer.group.size()-registerNum;
        //异常通道数量
        int warnNum=NettyContainer.warnSet.size();

        monitor.getRegisterNum().setText(String.valueOf(registerNum));

        monitor.getUncheckedNum().setText(String.valueOf(uncheckedNum));
        monitor.getWarnNum().setText(String.valueOf(warnNum));


        for (Map.Entry<String, Channel> entry : NettyContainer.sourceChannels.entrySet()) {
            String key = entry.getKey();
            buildData(key,ChannelState.CHANNEL_RUNNING);
        }

        for (String key : NettyContainer.warnSet) {
            buildData(key,ChannelState.CHANNEL_CLOSED);

        }

        //刷新数据
        monitor.getDml().setVector(this.vector);
        monitor.getjTable().updateUI();
    }

    private void buildData(String key,String state) {
        boolean b=true;
        SortVector<String> sortVector=null;
        //检验是否已经存在显示数据中，如果存在则更新，不存在则新增
        for (SortVector<String> strings : vector) {
            String s = strings.get(0);
            if(s.equals(key)){
                b=false;
                sortVector=strings;
                break;
            }
        }

        if(b){
            sortVector = new SortVector<String>();
            sortVector.add(key);
            sortVector.add(state);
            vector.add(sortVector);
        }else{
            sortVector.set(1,state);
        }
    }

    public void executor() {
        scheduledThreadPool.scheduleWithFixedDelay(this, 60000, 10000, TimeUnit.MILLISECONDS);
    }
}

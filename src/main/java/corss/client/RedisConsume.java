package corss.client;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.ProtocolFactory;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.EquipmentRecUART;
import corss.server.netty.protocol.receive.FaultUART;
import corss.util.RedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author lianrongfa
 * @date 2018/8/27
 */
public class RedisConsume {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();

        for (;;){


            List<String> cross = jedis.blpop(6, "cross");
            if(cross.size()>0){
                String json = cross.get(1);
                JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
                Byte mark = jsonObject.getByte("mark");

                Class byMark = ProtocolFactory.getClassByMark(mark);

                UART o = (UART)JSONObject.parseObject(json, byMark);

                System.out.println(o.toString());
            }
            System.out.println("~~~~~~~~");


        /*Class aClass = ProtocolFactory.getClassByMark((byte)20);

        UART uart = (UART)JSONObject.parseObject(cross, aClass);

        String idString = uart.getIdString();

        NettyContainer.getChannel(idString);*/

        }
        //JSONObject jsonObject = JSONObject.parseObject(cross);


    }
}

package corss.server.netty.protocol;

import corss.server.netty.protocol.receive.FaultUART;
import corss.server.netty.protocol.receive.JobHistoryUART;
import corss.server.netty.protocol.send.RecordUART;
import corss.server.netty.protocol.receive.ReplyUART;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/5/22.
 */
public class ProtocolFactory {

    private static Map<Byte,Class> map=new HashMap<Byte,Class>();

    static {
        //可用配置文件方式
        map.put((byte) 65,FaultUART.class);
        map.put((byte) 66,FaultUART.class);
        map.put((byte) 67,FaultUART.class);
        map.put((byte) 68,JobHistoryUART.class);
        map.put((byte) 20,ReplyUART.class);
        map.put((byte) 69,RemoteSettingUART.class);
        map.put((byte) 97,RemoteSettingUART.class);
        map.put((byte) 98,RecordUART.class);
    }

    public static UART createUART(byte mark,byte [] data){
        Class clazz = map.get(mark);
        if(clazz!=null){
            UART uart;
            try {
                uart= (UART)clazz.newInstance();
                uart.setData(data);
                uart.parse();
                return uart;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

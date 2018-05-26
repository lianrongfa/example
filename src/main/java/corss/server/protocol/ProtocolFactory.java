package corss.server.protocol;

import corss.server.protocol.receive.FaultUART;
import corss.server.protocol.receive.JobHistoryUART;
import corss.server.protocol.send.RecordUART;
import corss.server.protocol.receive.ReplyUART;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/5/22.
 */
public class ProtocolFactory {

    private static Map<Byte,Class> map=new HashMap<Byte,Class>();

    static {
        //可用配置文件方式
        map.put((byte) 10,FaultUART.class);
        map.put((byte) 11,FaultUART.class);
        map.put((byte) 12,FaultUART.class);
        map.put((byte) 13,JobHistoryUART.class);
        map.put((byte) 20,ReplyUART.class);
        map.put((byte) 30,SettingUART.class);
        map.put((byte) 50,SettingUART.class);
        map.put((byte) 51,RecordUART.class);
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

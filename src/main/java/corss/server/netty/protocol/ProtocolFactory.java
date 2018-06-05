package corss.server.netty.protocol;

import corss.server.netty.protocol.receive.*;
import corss.server.netty.protocol.send.*;

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
        map.put((byte) 69,RemoteSettingRecUART.class);
        map.put((byte) 70,EquipmentRecUART.class);
        map.put((byte) 97,PullSettingUART.class);
        map.put((byte) 98,RecordUART.class);//--
        map.put((byte) 99,DateSettingUART.class);
        map.put((byte) 100,EquipmentSendUART.class);//--
        map.put((byte) 101,RemoteSettingSendUART.class);
        map.put((byte) 71,RecSeerUART.class);
        map.put((byte) 102,SendSeerUART.class);
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

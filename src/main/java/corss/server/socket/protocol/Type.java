package corss.server.socket.protocol;

import corss.server.netty.protocol.send.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/5/28.
 */
public abstract class Type {

    private final static Map<Integer, Class> map = new HashMap<>();

    static {
        map.put(1, RemoteSettingSendUART.class);//远程参数设定
        map.put(2, RecordUART.class);//记录调取
        map.put(3, DateSettingUART.class);//日期时间设定
        map.put(4, PullSettingUART.class);//参数调取
        map.put(5, EquipmentSendUART.class);//调取手机号码及机器编码
    }

    public static Class getClazz(int type) {
        return map.get(type);
    }

}

package corss.server.socket.protocol;

import corss.server.netty.protocol.RemoteSettingUART;
import corss.server.netty.protocol.send.DateSettingUART;
import corss.server.netty.protocol.send.PullSettingUART;
import corss.server.netty.protocol.send.RecordUART;

/**
 * Created by lianrongfa on 2018/5/28.
 */
public enum Type {
    REMOTE_SETTING(1, RemoteSettingUART.class),//远程参数设定
    RECORD(2, RecordUART.class),//记录调取
    DATE_SETTING(3, DateSettingUART.class),//日期时间设定
    PULL_SETTING(4, PullSettingUART.class)//参数调取
    ;
    private int type;
    private Class clazz;

    Type(int type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public static Class getClazz(int type){
        for (Type item : values()) {
            if(item.getType()==type){
                return item.getClazz();
            }
        }
        return null;
    }
}

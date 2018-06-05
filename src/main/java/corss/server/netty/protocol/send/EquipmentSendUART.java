package corss.server.netty.protocol.send;

import corss.configuration.ConfigContext;
import corss.configuration.ProtocolConfig;
import corss.server.netty.protocol.AbstractUART;
import io.netty.util.AsciiString;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lianrongfa on 2018/5/22.
 * 手机号码及设备编号 下传
 */
public class EquipmentSendUART extends AbstractUART {

    //手机号
    private String phoneNum;

    public EquipmentSendUART() {
        super((byte) 100,'1');
    }

    @Override
    public void parse() {
        byte b = (byte) getType();
        this.data[1]=b;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}

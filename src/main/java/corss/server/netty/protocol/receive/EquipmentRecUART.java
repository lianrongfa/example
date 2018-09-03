package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;

import java.util.Arrays;

/**
 * Created by lianrongfa on 2018/5/22.
 * 手机号码及设备编号 上传
 *
 */
public class EquipmentRecUART extends AbstractUART {
    public EquipmentRecUART() {
    }

    //手机号
    private String phoneNum;

    @Override
    public void parse() {

        char markChar = (char) getMarks()[1];
        setType(markChar);
        if('0'==markChar){//手机号
            byte[] bytes = Arrays.copyOfRange(this.data, 2, 13);
            setPhoneNum(asciiString(bytes));
        }
        if('1'==markChar){//设备编码
            byte[] bytes = Arrays.copyOfRange(this.data, 2, 6);
            setIdString(asciiString(bytes));
        }
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}

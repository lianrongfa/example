package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 * 故障类：设备故障上传/作业违章上传/启动预警上传(共9B)
 */
public class FaultUART extends AbstractUART {

    //故障时间
    private Date faultTime;


    public FaultUART() {
    }

    public FaultUART(byte[] data) {
        super(data);
    }

    public Date getFaultTime() {
        return this.faultTime;
    }

    public void setFaultTime(Date faultTime) {
        this.faultTime = faultTime;
    }

    private void parseFaultTime(int from, int to) {
        byte[] bytes = Arrays.copyOfRange(getData(), from, to);

        String s = null;
        try {
            s = new String(bytes, "US-ASCII");
            if (s.length() == 12)
                s = prefix + s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Date date = buildDateTime(s);

        setFaultTime(date);
    }

    public void main(String[] args) {
        FaultUART uart = new FaultUART(new byte[]{65, 0x30, 0x31, 0x38, 0x30, 0x35, 0x32, 0x32, 0x30, 0x34, 0x32, 0x34, 0x34, 0x31});
        uart.parse();
        System.out.println(uart.getFaultTime());
    }

    @Override
    public void parse() {
        parseFaultTime(2, getData().length);
    }
}

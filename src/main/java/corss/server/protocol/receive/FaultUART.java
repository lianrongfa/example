package corss.server.protocol.receive;

import corss.server.protocol.AbstractUART;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 *  故障类：设备故障上传/作业违章上传/启动预警上传(共9B)
 */
public class FaultUART extends AbstractUART {

    //故障时间
    private Date faultTime;


    public FaultUART() {
    }

    public FaultUART(byte[] data) {
        super(data);
    }

    public Date getFaultTime(){
        return this.faultTime;
    }

    public void setFaultTime(Date faultTime) {
        this.faultTime = faultTime;
    }

    private void parseFaultTime(int from, int to){
        byte[] bytes = Arrays.copyOfRange(getData(), from, to);

        Date date = buildTime(bytes);

        setFaultTime(date);
    }

    public void main(String [] args){
        FaultUART uart = new FaultUART(new byte[]{10,01,18,5,23,14,30,7,18});

        byte[] bytes = Arrays.copyOfRange(uart.getData(), 5,uart.getData().length-1);

        Date date = uart.buildTime(bytes);

        String format = uart.sdyyyyMMddHHmmss.format(date);

        System.out.println(format);
    }

    @Override
    public void parse() {
        parseFaultTime(2,7);
    }
}

package corss.server.protocol;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public abstract class AbstractUART implements UART {

    protected Calendar calendar=Calendar.getInstance();
    protected SimpleDateFormat sdyyyyMMddHHmmss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected SimpleDateFormat sdyyyyMMddHHmm=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 协议最小长度
     */
    public final static int MIN_LENGTH = 3;
    /**
     * 协议最大长度
     */
    public final static int MAX_LENGTH = 55;

    /**
     * 内容,具体查阅文档《道口作业记录仪与数据中心通信数据协议》
     */
    protected byte [] data;

    public AbstractUART() {
    }

    protected AbstractUART(byte[] data) {
        this.data = data;
    }

    public byte [] getData() {
        return this.data;
    }

    public void setData(byte [] data) {
        this.data = data;
    }

    public byte [] getMark() {
        if (data.length > MIN_LENGTH) {
            byte[] bytes = {data[0], data[1]};
            return bytes;
        }
        return null;
    }

    public String getMarkString(){
        if (data.length > MIN_LENGTH) {
            return data[0]+""+data[1];
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Byte item :this.data) {
            sb.append(item);
        }
        return sb.toString();
    }


    @Override
    public byte [] getId() {
        return null;
    }

    @Override
    public String getIdString() {
        return null;
    }

    /**
     * @param bytes byte数组
     * @return 类型 2018-5-23 15:21:03
     */
    protected Date buildDateTimes(byte[] bytes) {
        StringBuilder sb = new StringBuilder("20");
        //2018-05-13 14:30:11
        sb.append(bytes[0]);
        sb.append("-");
        sb.append(bytes[1]);
        sb.append("-");
        sb.append(bytes[2]);
        sb.append(" ");

        sb.append(bytes[3]);
        sb.append(":");
        sb.append(bytes[4]);
        sb.append(":");
        sb.append(bytes[5]);

        try {
            return this.sdyyyyMMddHHmmss.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param bytes byte数组
     * @return 类型 2018-5-23 15:21
     */
    protected Date buildDateTime(byte[] bytes) {
        StringBuilder sb = new StringBuilder("20");
        sb.append(bytes[0]);
        sb.append("-");
        sb.append(bytes[1]);
        sb.append("-");
        sb.append(bytes[2]);
        sb.append(" ");

        sb.append(bytes[3]);
        sb.append(":");
        sb.append(bytes[4]);

        try {
            return this.sdyyyyMMddHHmm.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bytes byte数组
     * @return 类型 15:21:01
     */
    protected Date buildTime(byte[] bytes) {
        Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder(year + "-" + month + "-" + day + " ");

        sb.append(bytes[0]);
        sb.append(":");
        sb.append(bytes[1]);
        sb.append(":");
        sb.append(bytes[2]);
        try {
            return this.sdyyyyMMddHHmmss.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

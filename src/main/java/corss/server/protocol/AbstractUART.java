package corss.server.protocol;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public abstract class AbstractUART implements UART {

    //即为20xx年
    protected static final String prefix="20";

    protected SimpleDateFormat yyyyMMddHHmmss =new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 协议最小长度
     */
    public final static int MIN_LENGTH = 2;
    /**
     * 协议最大长度
     */
    public final static int MAX_LENGTH = 98;

    //类日期 格式为：18526 即为2018年5月26日
    private String datePrefix;

    /**
     * 内容,具体查阅文档《道口作业记录仪与数据中心通信数据协议》
     */
    protected byte [] data;

    public AbstractUART() {
    }

    public String getDatePrefix() {
        return datePrefix;
    }

    public void setDatePrefix(String datePrefix) {
        this.datePrefix = datePrefix;
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
            try {
                return new String(new byte[]{data[0],data[1]},"US-ASCII");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
    @Deprecated
    protected Date buildDateTimes(byte[] bytes) {
        /*StringBuilder sb = new StringBuilder("20");
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
            return this.yyyyMMddHHmmss.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return null;
    }


    /**
     * @param bytes byte数组
     * @return 类型 2018-5-23 15:21
     */
    @Deprecated
    protected Date buildDateTime(byte[] bytes) {
        /*StringBuilder sb = new StringBuilder("20");
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
        }*/
        return null;
    }

    /**
     * @param bytes byte数组
     * @return 类型 15:21:01
     */
    @Deprecated
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
            return this.yyyyMMddHHmmss.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dateTime 日期时间 类型为20180526152108 即为2018年5月26日15:21:08
     * @return
     */
    protected Date buildDateTime(String dateTime) {
        if(dateTime!=null){
            try {
                return this.yyyyMMddHHmmss.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param time 时间 格式为：152101 即为15点21分01秒
     * @return
     */

    protected Date buildTime(String time) {
        return null;
    }

}

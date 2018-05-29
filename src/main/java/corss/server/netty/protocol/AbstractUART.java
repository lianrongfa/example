package corss.server.netty.protocol;

import corss.configuration.ConfigContext;
import corss.configuration.ProtocolConfig;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public abstract class AbstractUART implements UART {

    //即为20xx年,如果该软件使用到了3000年，改为30一次类推，不过应该不可能~_~
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

    //标识码
    protected byte mark;

    //操作码
    protected byte type;

    //设备id
    protected String equipmentId;
    /**
     * 内容,具体查阅文档《道口作业记录仪与数据中心通信数据协议》
     */
    protected byte [] data;

    public AbstractUART() {
    }


    /**
     * 用于发送协议初始化
     * @param mark 识别码
     * @param type 操作码
     */
    public AbstractUART(byte mark, byte type) {
        this.mark = mark;
        this.type = type;

        ProtocolConfig protocolConfig = ConfigContext.getInstace().getProtocolConfig();
        Integer size = protocolConfig.getReceiveMap().get(mark);
        this.setData(new byte[size]);

        //填充byte数组
        data[0]=this.mark;
        data[1]=this.type;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDatePrefix() {
        return prefix+datePrefix;
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

    public static String asciiString(byte [] bytes){
        if(bytes==null||bytes.length<1) return null;
        try {
            return new String(bytes,"US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
     * 将日期格式化为 byte数组
     * @param date 日期
     * @return
     */
    protected byte [] getTimeByte(Date date){
        if(date!=null){
            String s = this.yyyyMMddHHmmss.format(date);
            if(s.length()==14){
                s=s.substring(2);
            }
            try {
                return s.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将byte数组插入到指定下标
     * @param bytes 插入数组
     * @param idx 插下标
     * @return 插入结束下标
     */
    protected int insertArr(byte [] bytes,int idx){
        if (bytes!=null) {
            for (int i=0;i<bytes.length;i++) {
                data[i+idx]=bytes[i];
            }
            return idx+bytes.length;
        }
        return idx;
    }
}

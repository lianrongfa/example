package corss.server.protocol.send;

import corss.configuration.ConfigContext;
import corss.configuration.ProtocolConfig;
import corss.server.protocol.AbstractUART;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 *  记录调取(共14B) 完成
 */
public class RecordUART extends AbstractUART {
    //识别码
    private final byte mark=(byte)98;
    //调取类型
    private byte type;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    public RecordUART() {
    }

    /**
     * 改构造方法用于构造发送数据
     * @param type RecordUART中的类型
     * @param startTime 开始时间  18 5 23 16 55
     * @param endTime 结束时间  18 5 23 16 55
     */
    public RecordUART(byte type, Date startTime, Date endTime) {
        super();
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;

        ProtocolConfig protocolConfig = ConfigContext.getInstace().getProtocolConfig();
        Integer size = protocolConfig.getReceiveMap().get(mark);

        this.setData(new byte[size]);

        //填充byte数组
        data[0]=this.mark;
        data[1]=this.type;

        int idx=2;

        byte[] startBytes = getTimeByte(this.startTime);
        byte[] endTimeBytes = getTimeByte(this.endTime);

        int i = insertArr(endTimeBytes, insertArr(startBytes, idx));


    }

    protected int insertArr(byte [] bytes,int idx){
        if (bytes!=null) {
            for (int i=0;i<bytes.length;i++) {
                data[i+idx]=bytes[i];
            }
            return idx+bytes.length;
        }
        return idx;
    }

    @Override
    public void parse() {

    }

    public static void main(String[] args) {
        /*String start = "18 5 23 16 55";
        String end = "18 5 23 18 55";*/

        RecordUART uart = new RecordUART(Type.ALL.getCode(), new Date(), new Date());

        System.out.println();

    }

    private byte [] getTimeByte(Date date){
        if(date!=null){
            /*byte[] bytes = new byte[6];
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            bytes[0] = Byte.parseByte((byte) (year % 100)+"");
            bytes[1]=(byte)(calendar.get(Calendar.MONTH)+1);
            bytes[2]=(byte)(calendar.get(Calendar.DAY_OF_MONTH));
            bytes[3]=(byte)(calendar.get(Calendar.HOUR_OF_DAY));
            bytes[4]=(byte)(calendar.get(Calendar.MINUTE));
            bytes[5]=(byte)(calendar.get(Calendar.SECOND));*/
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
     * 调取种类
     */
    enum Type{
        ALL((byte)0x30,"全部调取"),
        OUT((byte)0x31,"出场晚"),
        OFF((byte)0x32,"关杆晚"),
        ON((byte)0x33,"开杆早"),
        FAULT((byte)0x34,"设备故障"),
        WARNING((byte)0x35,"预警");

        private byte code;
        private String dessc;

        Type(byte code, String dessc) {
            this.code = code;
            this.dessc = dessc;
        }

        public byte getCode() {
            return code;
        }

        public String getDessc() {
            return dessc;
        }
    }
}

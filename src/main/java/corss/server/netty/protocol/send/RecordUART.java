package corss.server.netty.protocol.send;

import corss.server.netty.protocol.AbstractUART;

import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 *  记录调取(共14B) 完成
 */
public class RecordUART extends AbstractUART {
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    public RecordUART() {
        super((byte)98,'0');
    }

    /**
     * 该构造方法用于构造发送数据
     * @param type RecordUART中的类型
     * @param startTime 开始时间  18 5 23 16 55
     * @param endTime 结束时间  18 5 23 16 55
     */
    public RecordUART(char type, Date startTime, Date endTime) {
        super((byte)98,type);

        this.startTime = startTime;
        this.endTime = endTime;

        int idx=2;

        byte[] startBytes = getTimeByte(this.startTime);
        byte[] endTimeBytes = getTimeByte(this.endTime);

        int i = insertArr(endTimeBytes, insertArr(startBytes, idx));
    }

    @Override
    public void parse() {

        //重新填充byte数组
        data[0]=this.mark;
        data[1]= (byte) this.type;

        /*int idx=2;

        byte[] startBytes = getTimeByte(this.startTime);
        byte[] endTimeBytes = getTimeByte(this.endTime);

        int i = insertArr(endTimeBytes, insertArr(startBytes, idx));*/
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    /**
     * 调取种类
     */
    enum Type{
        ALL((byte)'0',"全部调取"),
        OUT((byte)'1',"出场晚"),
        OFF((byte)'2',"关杆晚"),
        ON((byte)'3',"开杆早"),
        FAULT((byte)'4',"设备故障"),
        WARNING((byte)'5',"预警");

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

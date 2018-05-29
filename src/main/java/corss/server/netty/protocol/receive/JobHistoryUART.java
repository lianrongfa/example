package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 * 详细作业记录上传(共55B)
 */
public class JobHistoryUART extends AbstractUART {

    private static String [] fieldNames ={
            "advance","marchOut","startAlert","endAlert","startAOff",
            "endAOff","startBOff","endBOff","startReach","endReach",
            "startAOn","endAOn","startBOn","endBOn"};
    /**
     * 上/下预告
     */
    private Date advance;
    /**
     * 出场
     */
    private Date marchOut;
    /**
     * 警报始
     */
    private Date startAlert;
    /**
     * 警报末
     */
    private Date endAlert;
    /**
     * A杆关闭始
     */
    private Date startAOff;
    /**
     * A杆关闭末
     */
    private Date endAOff;
    /**
     * B杆关闭始
     */
    private Date startBOff;
    /**
     * B杆关闭末
     */
    private Date endBOff;
    /**
     * 到达始
     */
    private Date startReach;
    /**
     * 到达末
     */
    private Date endReach;
    /**
     * A杆打开始
     */
    private Date startAOn;
    /**
     * A杆打开末
     */
    private Date endAOn;
    /**
     * B杆打开始
     */
    private Date startBOn;
    /**
     * B杆打开末
     */
    private Date endBOn;
    /**
     * 预告
     */
    private byte advanceState;
    /**
     * 出场
     */
    private byte marchOutState;

    /**
     * 报警
     */
    private byte alertState;

    /**
     * 关杆
     */
    private byte offState;
    /**
     * 到达
     */
    private byte reachState;
    /**
     * 开杆
     */
    private byte onState;

    @Override
    public void parse() {
        int from=8,to=14,increment=6;//可做成配置的

        //设置日期前缀
        byte[] datePrefix = Arrays.copyOfRange(this.data, 2, 8);
        setDatePrefix(asciiString(datePrefix));

        //解析日期
        Class clazz = this.getClass();
        for (String fieldName : this.fieldNames) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                byte[] bytes = Arrays.copyOfRange(this.data, from, to);
                String s = asciiString(bytes);
                if(checkString(s)){
                    Date date = yyyyMMddHHmmss.parse(getDatePrefix() + s);
                    field.set(this,date);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            from=from+increment;
            to=to+increment;
        }
    }

    private boolean checkString(String s) {
        if(s==null){
            return false;
        }
        return !s.contains(" ");
    }

    public static void main(String[] args) {
        byte[] bytes = {68,0x30,0x31,0x38,0x30,0x35,0x32,0x32,0x30,0x34,0x32,0x33,0x34,0x31,
                0x20,0x20,0x32,0x33,0x34,0x31,0x30,0x34,0x32,0x33,0x34,0x31,
                0x30,0x34,0x32,0x33,0x34,0x31,0x30,0x34,0x32,0x33,0x34,0x31,
                0x30,0x34,0x32,0x33,0x34,0x31,0x30,0x34,0x32,0x33,0x34,0x31,
                0x30,0x34,0x32,0x33,0x34,0x31,0x30,0x34,0x32,0x33,0x34,0x31,
                0x30,0x34,0x32,0x33,0x34,0x31,0x30,0x34,0x32,0x33,0x34,0x31,
                0x30,0x34,0x32,0x33,0x34,0x31,0x30,0x34,0x32,0x33,0x34,0x31,
                0x30,0x34,0x32,0x33,0x34,0x31,0x31,0x33,0x32,0x32,0x31,0x31
                };

        JobHistoryUART uart = new JobHistoryUART();
        uart.setData(bytes);
        uart.parse();
        System.out.println();

    }

    public Date getAdvance() {
        return advance;
    }

    public Date getMarchOut() {
        return marchOut;
    }

    public Date getStartAlert() {
        return startAlert;
    }

    public Date getEndAlert() {
        return endAlert;
    }

    public Date getStartAOff() {
        return startAOff;
    }

    public Date getEndAOff() {
        return endAOff;
    }

    public Date getStartBOff() {
        return startBOff;
    }

    public Date getEndBOff() {
        return endBOff;
    }

    public Date getStartReach() {
        return startReach;
    }

    public Date getEndReach() {
        return endReach;
    }

    public Date getStartAOn() {
        return startAOn;
    }

    public Date getEndAOn() {
        return endAOn;
    }

    public Date getStartBOn() {
        return startBOn;
    }

    public Date getEndBOn() {
        return endBOn;
    }

    public byte getAdvanceState() {
        return advanceState;
    }

    public byte getMarchOutState() {
        return marchOutState;
    }

    public byte getAlertState() {
        return alertState;
    }

    public byte getOffState() {
        return offState;
    }

    public byte getReachState() {
        return reachState;
    }

    public byte getOnState() {
        return onState;
    }

}

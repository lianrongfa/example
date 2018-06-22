package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 * 详细作业记录上传(共98B)
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
    private char advanceState;
    /**
     * 出场
     */
    private char marchOutState;

    /**
     * 报警
     */
    private char alertState;

    /**
     * 关杆
     */
    private char offState;
    /**
     * 到达
     */
    private char reachState;
    /**
     * 开杆
     */
    private char onState;

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

        //预告
        this.advanceState=(char)this.data[92];
        /// 出场
        this.marchOutState=(char)this.data[93];
        //报警
        this.alertState=(char)this.data[94];
        // 关杆
        this.offState=(char)this.data[95];
        //到达
        this.reachState=(char)this.data[96];
        //开杆
        this.onState=(char)this.data[97];
    }

    private boolean checkString(String s) {
        if(s==null){
            return false;
        }
        return !s.contains(" ");
    }

    /*public static void main(String[] args) {
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

    }*/

    public static String[] getFieldNames() {
        return fieldNames;
    }

    public static void setFieldNames(String[] fieldNames) {
        JobHistoryUART.fieldNames = fieldNames;
    }

    public Date getAdvance() {
        return advance;
    }

    public void setAdvance(Date advance) {
        this.advance = advance;
    }

    public Date getMarchOut() {
        return marchOut;
    }

    public void setMarchOut(Date marchOut) {
        this.marchOut = marchOut;
    }

    public Date getStartAlert() {
        return startAlert;
    }

    public void setStartAlert(Date startAlert) {
        this.startAlert = startAlert;
    }

    public Date getEndAlert() {
        return endAlert;
    }

    public void setEndAlert(Date endAlert) {
        this.endAlert = endAlert;
    }

    public Date getStartAOff() {
        return startAOff;
    }

    public void setStartAOff(Date startAOff) {
        this.startAOff = startAOff;
    }

    public Date getEndAOff() {
        return endAOff;
    }

    public void setEndAOff(Date endAOff) {
        this.endAOff = endAOff;
    }

    public Date getStartBOff() {
        return startBOff;
    }

    public void setStartBOff(Date startBOff) {
        this.startBOff = startBOff;
    }

    public Date getEndBOff() {
        return endBOff;
    }

    public void setEndBOff(Date endBOff) {
        this.endBOff = endBOff;
    }

    public Date getStartReach() {
        return startReach;
    }

    public void setStartReach(Date startReach) {
        this.startReach = startReach;
    }

    public Date getEndReach() {
        return endReach;
    }

    public void setEndReach(Date endReach) {
        this.endReach = endReach;
    }

    public Date getStartAOn() {
        return startAOn;
    }

    public void setStartAOn(Date startAOn) {
        this.startAOn = startAOn;
    }

    public Date getEndAOn() {
        return endAOn;
    }

    public void setEndAOn(Date endAOn) {
        this.endAOn = endAOn;
    }

    public Date getStartBOn() {
        return startBOn;
    }

    public void setStartBOn(Date startBOn) {
        this.startBOn = startBOn;
    }

    public Date getEndBOn() {
        return endBOn;
    }

    public void setEndBOn(Date endBOn) {
        this.endBOn = endBOn;
    }

    public char getAdvanceState() {
        return advanceState;
    }

    public void setAdvanceState(char advanceState) {
        this.advanceState = advanceState;
    }

    public char getMarchOutState() {
        return marchOutState;
    }

    public void setMarchOutState(char marchOutState) {
        this.marchOutState = marchOutState;
    }

    public char getAlertState() {
        return alertState;
    }

    public void setAlertState(char alertState) {
        this.alertState = alertState;
    }

    public char getOffState() {
        return offState;
    }

    public void setOffState(char offState) {
        this.offState = offState;
    }

    public char getReachState() {
        return reachState;
    }

    public void setReachState(char reachState) {
        this.reachState = reachState;
    }

    public char getOnState() {
        return onState;
    }

    public void setOnState(char onState) {
        this.onState = onState;
    }
}

package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/5/22.
 * 参数设定上传 / 远程参数设定(共98B)
 */
public class RemoteSettingRecUART extends AbstractUART {
    private final static Map<String, Relation> relationMap = new HashMap<>();

    static {
        relationMap.put("crossLineType",new Relation(1,2));
        relationMap.put("mileage",new Relation(3,3));
        relationMap.put("meter",new Relation(3,6));
        relationMap.put("oneTwoLine",new Relation(1,9));
        relationMap.put("handrailType",new Relation(1,10));
        relationMap.put("peacetimeState",new Relation(1,11));
        relationMap.put("guardState",new Relation(1,12));
        relationMap.put("marchOut",new Relation(1,13));//
        relationMap.put("earlyWarning",new Relation(1,14));
        relationMap.put("voiceState",new Relation(1,15));
        relationMap.put("dataUpload",new Relation(1,16));
        relationMap.put("dataUploadType",new Relation(1,17));//
        relationMap.put("fault",new Relation(1,18));
        relationMap.put("closeRecord",new Relation(1,19));
        relationMap.put("reviseTime",new Relation(1,20));
        relationMap.put("warnNotOut",new Relation(2,21));
        relationMap.put("warnNotOff",new Relation(2,23));
        relationMap.put("earlyOn",new Relation(2,25));
        relationMap.put("upStartWarn",new Relation(2,27));
        relationMap.put("downStartWarn",new Relation(2,29));
        relationMap.put("onGuardTime",new Relation(2,31));
        relationMap.put("voiceOffStart",new Relation(2,33));
        relationMap.put("voiceOffEnd",new Relation(2,35));

    }

    /**
     * 道口线别 字符类型
     * 0-滨洲线
     * 1-滨绥线
     * 2-绥佳线
     * 3-齐北线
     * 4-富嫩线
     * 5-滨北线
     * 6-拉滨线
     * 7-嫩林线
     * 8-牙林线
     * 9-北黑线
     * A-林密线
     * ....具体参照协议文档
     */
    private char crossLineType;
    /**
     * 公里 范围:000-999 字符串类型
     */
    private String mileage;
    /**
     * 米 范围:000-999 字符串类型
     */
    private String meter;

    /**
     * 单复线 0单 1复 字符类型
     */
    private char oneTwoLine;

    /**
     * 栏杆类型 0跑车 1栏杆 字符类型
     */
    private char handrailType;

    /**
     * 平时状态 0开杆 1关杆 字符类型
     */
    private char peacetimeState;
    /**
     * 在岗按钮 0启用 1停止 字符类型
     */
    private char guardState;
    /**
     * 出场按钮 0启用 1停止 字符类型
     */
    private char marchOut;

    /**
     * 自动预警 0启用 1停止 字符类型
     */
    private char earlyWarning;

    /**
     * 语音状态 0启用 1停止 字符类型
     */
    private char voiceState;
    /**
     * 数据上传 0启用 1停止 字符类型
     */
    private char dataUpload;
    /**
     * 上传种类 0全部 1违章 字符类型
     */
    private char dataUploadType;

    /**
     * 设备故障 0上传 1禁止 字符类型
     */
    private char fault;
    /**
     * 关杆记录 0启用 1停止 字符类型
     */
    private char closeRecord;
    /**
     * 校正时间 0自动 1手动 字符类型
     */
    private char reviseTime;
    /**
     * 报警不出 范围:00-99 字符串类型
     */
    private String warnNotOut;

    /**
     * 报警不关杆 范围:00-99 字符串类型
     */
    private String warnNotOff;

    /**
     * 开杆早 范围:00-99 字符串类型
     */
    private String earlyOn;

    /**
     * 上行启动预警 范围:00-99 字符串类型
     */
    private String upStartWarn;

    /**
     * 下行启动预警 范围:00-99 字符串类型
     */
    private String downStartWarn;

    /**
     * 在岗时间 范围:00-99 字符串类型
     */
    private String onGuardTime;

    /**
     * 语音关闭始 范围:00-99 字符串类型
     */
    private String voiceOffStart;
    /**
     * 语音关闭末 范围:00-99 字符串类型
     */
    private String voiceOffEnd;

    public RemoteSettingRecUART() {
        super((byte) 97, '1');
    }

    @Override
    public void parse() {
        Class clazz = this.getClass();
        for (Map.Entry<String, Relation> relationEntry : relationMap.entrySet()) {
            String key = relationEntry.getKey();
            Relation relation = relationEntry.getValue();
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);

                int idx = relation.getIdx();
                int length = relation.getLength();

                byte[] bytes = Arrays.copyOfRange(this.data, idx, idx + length);
                
                if(bytes!=null){
                    int bLength = bytes.length;
                    if (bLength==1){
                        char aChar = (char) bytes[0];
                        field.set(this,aChar);
                    }else{
                        String s = asciiString(bytes);
                        field.set(this,s);
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    static class Relation {
        private int length;
        private int idx;

        public Relation(int length, int idx) {
            this.length = length;
            this.idx = idx;
        }

        public int getLength() {
            return length;
        }

        public int getIdx() {
            return idx;
        }
    }


    /**
     * 远程参数设定上传使用
     *
     * @param mark
     */
    public void setMark(byte mark) {
        this.mark = mark;
    }

    public char getCrossLineType() {
        return crossLineType;
    }

    public void setCrossLineType(char crossLineType) {
        this.crossLineType = crossLineType;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public char getOneTwoLine() {
        return oneTwoLine;
    }

    public void setOneTwoLine(char oneTwoLine) {
        this.oneTwoLine = oneTwoLine;
    }

    public char getHandrailType() {
        return handrailType;
    }

    public void setHandrailType(char handrailType) {
        this.handrailType = handrailType;
    }

    public char getPeacetimeState() {
        return peacetimeState;
    }

    public void setPeacetimeState(char peacetimeState) {
        this.peacetimeState = peacetimeState;
    }

    public char getGuardState() {
        return guardState;
    }

    public void setGuardState(char guardState) {
        this.guardState = guardState;
    }

    public char getEarlyWarning() {
        return earlyWarning;
    }

    public void setEarlyWarning(char earlyWarning) {
        this.earlyWarning = earlyWarning;
    }

    public char getVoiceState() {
        return voiceState;
    }

    public void setVoiceState(char voiceState) {
        this.voiceState = voiceState;
    }

    public char getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(char dataUpload) {
        this.dataUpload = dataUpload;
    }

    public char getFault() {
        return fault;
    }

    public void setFault(char fault) {
        this.fault = fault;
    }

    public char getCloseRecord() {
        return closeRecord;
    }

    public void setCloseRecord(char closeRecord) {
        this.closeRecord = closeRecord;
    }

    public char getReviseTime() {
        return reviseTime;
    }

    public void setReviseTime(char reviseTime) {
        this.reviseTime = reviseTime;
    }

    public String getWarnNotOut() {
        return warnNotOut;
    }

    public void setWarnNotOut(String warnNotOut) {
        this.warnNotOut = warnNotOut;
    }

    public String getWarnNotOff() {
        return warnNotOff;
    }

    public void setWarnNotOff(String warnNotOff) {
        this.warnNotOff = warnNotOff;
    }

    public String getEarlyOn() {
        return earlyOn;
    }

    public void setEarlyOn(String earlyOn) {
        this.earlyOn = earlyOn;
    }

    public String getUpStartWarn() {
        return upStartWarn;
    }

    public void setUpStartWarn(String upStartWarn) {
        this.upStartWarn = upStartWarn;
    }

    public String getDownStartWarn() {
        return downStartWarn;
    }

    public void setDownStartWarn(String downStartWarn) {
        this.downStartWarn = downStartWarn;
    }

    public String getOnGuardTime() {
        return onGuardTime;
    }

    public void setOnGuardTime(String onGuardTime) {
        this.onGuardTime = onGuardTime;
    }

    public char getMarchOut() {
        return marchOut;
    }

    public void setMarchOut(char marchOut) {
        this.marchOut = marchOut;
    }

    public char getDataUploadType() {
        return dataUploadType;
    }

    public void setDataUploadType(char dataUploadType) {
        this.dataUploadType = dataUploadType;
    }

    public String getVoiceOffStart() {
        return voiceOffStart;
    }

    public void setVoiceOffStart(String voiceOffStart) {
        this.voiceOffStart = voiceOffStart;
    }

    public String getVoiceOffEnd() {
        return voiceOffEnd;
    }

    public void setVoiceOffEnd(String voiceOffEnd) {
        this.voiceOffEnd = voiceOffEnd;
    }
}

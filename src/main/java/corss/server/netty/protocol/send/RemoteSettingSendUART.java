package corss.server.netty.protocol.send;

import corss.server.netty.protocol.AbstractUART;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/5/22.
 * 远程参数设定(共14B)
 */
public class RemoteSettingSendUART extends AbstractUART {

    private final static Map<String, Relation> relationMap1 = new HashMap<>();
    private final static Map<String, Relation> relationMap2 = new HashMap<>();
    private final static Map<String, Relation> relationMap3 = new HashMap<>();

    static {
        relationMap1.put("crossLineType", new Relation(1, 2));
        relationMap1.put("mileage", new Relation(3, 3));
        relationMap1.put("meter", new Relation(3, 6));

        relationMap2.put("oneTwoLine", new Relation(1, 2));
        relationMap2.put("handrailType", new Relation(1, 3));
        relationMap2.put("peacetimeState", new Relation(1, 4));
        relationMap2.put("guardState", new Relation(1, 5));
        relationMap2.put("marchOut", new Relation(1, 6));//
        relationMap2.put("earlyWarning", new Relation(1, 7));
        relationMap2.put("voiceState", new Relation(1, 8));
        relationMap2.put("dataUpload", new Relation(1, 9));
        relationMap2.put("dataUploadType", new Relation(1, 10));//
        relationMap2.put("fault", new Relation(1, 11));
        relationMap2.put("closeRecord", new Relation(1, 12));
        relationMap2.put("reviseTime", new Relation(1, 13));

        relationMap3.put("warnNotOut", new Relation(1, 2));
        relationMap3.put("warnNotOff", new Relation(1, 3));
        relationMap3.put("earlyOn", new Relation(1, 4));
        relationMap3.put("upStartWarn", new Relation(1, 5));
        relationMap3.put("downStartWarn", new Relation(1, 6));
        relationMap3.put("onGuardTime", new Relation(1, 7));
        relationMap3.put("voiceOffStart", new Relation(1, 8));
        relationMap3.put("voiceOffEnd", new Relation(1, 9));

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
    private String warnNotOut = "  ";

    /**
     * 报警不关杆 范围:00-99 字符串类型
     */
    private String warnNotOff = "  ";

    /**
     * 开杆早 范围:00-99 字符串类型
     */
    private String earlyOn = "  ";

    /**
     * 上行启动预警 范围:00-99 字符串类型
     */
    private String upStartWarn = "  ";

    /**
     * 下行启动预警 范围:00-99 字符串类型
     */
    private String downStartWarn = "  ";

    /**
     * 在岗时间 范围:00-99 字符串类型
     */
    private String onGuardTime = "  ";

    /**
     * 语音关闭始 范围:00-99 字符串类型
     */
    private String voiceOffStart = "  ";
    /**
     * 语音关闭末 范围:00-99 字符串类型
     */
    private String voiceOffEnd = "  ";

    public RemoteSettingSendUART() {
        //type 默认为1
        super((byte) 101, '1');
    }

    @Override
    public void parse() {
        //重新根据mark、type字段填充data数组，因为参数设定、上传用同一个协议，但是识别码不同
        data[0] = this.mark;
        data[1] = (byte) this.type;

        Map<String, Relation> map = this.chooseMap();

        Class clazz = this.getClass();
        for (Map.Entry<String, Relation> relationEntry : map.entrySet()) {
            String key = relationEntry.getKey();
            Relation relation = relationEntry.getValue();
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);

                Object o = field.get(this);
                String s = String.valueOf(o);

                if (this.type == '2') {//限定时间
                    if (s != null) {
                        byte aByte = Byte.valueOf(s);
                        this.data[relation.getIdx()]=aByte;
                    }
                } else if (this.type == '1' || this.type == '0') {//基本参数、工作模式
                    if (s != null) {
                        int length = relation.getLength();
                        int idx = relation.getIdx();
                        while (s.length() < length) {
                            s = "0" + s;
                        }
                        byte[] bytes = s.getBytes("US-ASCII");
                        insertArr(bytes, idx);
                    }
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {

            } catch (UnsupportedEncodingException e) {
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

    private Map<String, Relation> chooseMap() {
        char c = this.getType();
        if (c == '0') {
            return relationMap1;
        } else if (c == '1') {
            return relationMap2;
        } else if (c == '2') {
            return relationMap3;
        }
        return null;
    }

    /**
     * 远程参数设定上传使用
     *
     * @param mark
     */
    public void setMark(byte mark) {
        this.mark = mark;
    }

    /**
     * 远程参数设定上传使用
     *
     * @param //type
     */
    /*public void setType(byte type) {
        this.type = type;
    }*/
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

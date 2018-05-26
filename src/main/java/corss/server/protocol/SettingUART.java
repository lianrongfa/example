package corss.server.protocol;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Properties;

/**
 * Created by lianrongfa on 2018/5/22.
 * 参数设定上传 / 远程参数设定(共8B)
 */
public class SettingUART extends AbstractUART {

    public static final byte _1 = 1;
    public static final byte _0 = 0;

    /**
     * 单复线 0单 1复
     */
    private byte oneTwoLine;

    /**
     * 栏杆类型 0跑车 1栏杆
     */
    private byte handrailType;

    /**
     * 平时状态 0开杆 1关杆
     */
    private byte peacetimeState;
    /**
     * 在岗状态 0启用 1停止
     */
    private byte guardState;

    /**
     * 出场按钮 0启用 1停止
     */
    private byte marchOut;//有错

    /**
     * 自动预警 0启用 1停止
     */
    private byte earlyWarning;

    /**
     * 语音状态 0启用 1停止
     */
    private byte voiceState;
    /**
     * 数据上传 0启用 1停止
     */
    private byte dataUpload;

    /**
     * 设备故障 0上传 1禁止
     */
    private byte fault;
    /**
     * 关杆记录 0启用 1停止
     */
    private byte closeRecord;
    /**
     * 校正时间 0自动 1手动
     */
    private byte reviseTime;

    @Override
    public void parse() {
        //暂且先固定为5、6可配置
        parseBin(5, 6);
    }

    @Test
    public void test() {

        byte[] bytes = {1, 0, 1, 0, 1, 1, 0, 1};
        int sum = binToDec(bytes);
        System.out.println(sum);

        byte[] bytes1 = new byte[8];
        bytes1[0]=1;
        bytes1[1]=1;
        bytes1[2]=1;

        System.out.println(binToDec(bytes1));
    }

    /**
     * 二进制数组转十进制byte
     * @param bytes
     * @return
     */
    private int binToDec(byte[] bytes) {
        int sum = 0;
        for (int i = bytes.length - 1, n = 0; i >= 0; i--, n++) {

            sum +=Math.pow(2, n) * bytes[i];
        }
        return sum;
    }

    /**
     * 解析十进制为二进制
     * @param idx8 8位的操作符地址
     * @param idx3 3位操作符地址
     */
    private void parseBin(int idx8, int idx3) {
        byte[] bytes = byteToBin(data[idx8]);
        //oneTwoLine, handrailType, peacetimeState, guardState, marchOut, earlyWarning, voiceState, dataUpload
        oneTwoLine = bytes[0];
        handrailType = bytes[1];
        peacetimeState = bytes[2];
        guardState = bytes[3];
        marchOut = bytes[4];
        earlyWarning = bytes[5];
        voiceState = bytes[6];
        dataUpload = bytes[7];

        byte[] nums = byteToBin(data[idx3]);
        fault = nums[0];
        closeRecord = nums[1];
        reviseTime = nums[2];
    }

    public byte[] byteToBin(byte b) {
        byte[] bytes = new byte[8];
        int num = 128;
        for (int i = 0; i < 8; i++) {
            int m = b << i;
            if ((m & num) == num) {
                bytes[i] = 1;
            } else {
                bytes[i] = 0;
            }
        }
        return bytes;
    }

    public byte getOneTwoLine() {
        return oneTwoLine;
    }

    public void setOneTwoLine(byte oneTwoLine) {
        this.oneTwoLine = oneTwoLine;
    }

    public byte getHandrailType() {
        return handrailType;
    }

    public void setHandrailType(byte handrailType) {
        this.handrailType = handrailType;
    }

    public byte getPeacetimeState() {
        return peacetimeState;
    }

    public void setPeacetimeState(byte peacetimeState) {
        this.peacetimeState = peacetimeState;
    }

    public byte getGuardState() {
        return guardState;
    }

    public void setGuardState(byte guardState) {
        this.guardState = guardState;
    }

    public byte getMarchOut() {
        return marchOut;
    }

    public void setMarchOut(byte marchOut) {
        this.marchOut = marchOut;
    }

    public byte getEarlyWarning() {
        return earlyWarning;
    }

    public void setEarlyWarning(byte earlyWarning) {
        this.earlyWarning = earlyWarning;
    }

    public byte getVoiceState() {
        return voiceState;
    }

    public void setVoiceState(byte voiceState) {
        this.voiceState = voiceState;
    }

    public byte getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(byte dataUpload) {
        this.dataUpload = dataUpload;
    }

    public byte getFault() {
        return fault;
    }

    public void setFault(byte fault) {
        this.fault = fault;
    }

    public byte getCloseRecord() {
        return closeRecord;
    }

    public void setCloseRecord(byte closeRecord) {
        this.closeRecord = closeRecord;
    }

    public byte getReviseTime() {
        return reviseTime;
    }

    public void setReviseTime(byte reviseTime) {
        this.reviseTime = reviseTime;
    }
}

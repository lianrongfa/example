package corss.server.protocol.receive;

import corss.server.protocol.AbstractUART;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 * 详细作业记录上传(共55B)
 */
public class JobHistoryUART extends AbstractUART {
    /**
     * 日期
     */
    private Date date;

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
     * 上预
     */
    private byte advanceState;
    /**
     * 出场
     */
    private byte marchOutState;

    /**
     * 上/下报
     */
    private byte alertState;

    /**
     * 关栏杆
     */
    private byte offState;
    /**
     * 上/下到
     */
    private byte reachState;
    /**
     * 开栏杆
     */
    private byte onState;
    /**
     * 预警
     */
    private byte earlyWarning;

    @Override
    public void parse() {





    }

    public Date getDate() {
        return date;
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

    public byte getEarlyWarning() {
        return earlyWarning;
    }
}

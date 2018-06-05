package corss.server.netty.protocol.send;

import corss.server.netty.protocol.AbstractUART;

import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/28.
 * 日期时间设定 size 14byte
 */
public class DateSettingUART extends AbstractUART {

    private Date settingDate;

    public DateSettingUART(Date date) {
        super((byte) 99, '0');

        byte[] startBytes = getTimeByte(date);

        insertArr(startBytes, 2);
    }

    public DateSettingUART() {
        super((byte) 99, '0');
    }

    @Override
    public void parse() {
        //byte[] startBytes = getTimeByte(this.settingDate);
        byte[] startBytes = getTimeByte(new Date());

        insertArr(startBytes, 2);
    }

    public Date getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(Date settingDate) {
        this.settingDate = settingDate;
    }
}

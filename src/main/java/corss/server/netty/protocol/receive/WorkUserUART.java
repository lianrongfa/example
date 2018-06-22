package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/6/21.
 * 当班道口员编号上传(9个字节)
 */
public class WorkUserUART extends AbstractUART {

    //设备id
    private String assetId;
    //当班道口人员编号(主)
    private String userMain;
    //当班道口人员编号(副)
    private String userVice;
    //当班时间
    private Date jobTime;

    public WorkUserUART() {
        this.jobTime = new Date();
    }

    @Override
    public void parse() {
       this.mark=data[0];
       this.userMain=asciiString(Arrays.copyOfRange(getData(),1,5));
       this.userVice=asciiString(Arrays.copyOfRange(getData(),5,data.length));
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getUserMain() {
        return userMain;
    }

    public void setUserMain(String userMain) {
        this.userMain = userMain;
    }

    public String getUserVice() {
        return userVice;
    }

    public void setUserVice(String userVice) {
        this.userVice = userVice;
    }

    public Date getJobTime() {
        return jobTime;
    }

    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }
}

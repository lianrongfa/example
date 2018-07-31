package corss.ui.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lianrongfa
 * @date 2018/7/4
 */
public class ChannelState {

    public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    public static final String CHANNEL_RUNNING="已连接";
    public static final String CHANNEL_CLOSED="已断开";

    public static final int SIZE=4;

    private int num;
    private String state="";
    //注册时间
    private String registerTime="";
    //异常时间
    private String warnTime;

    public ChannelState() {
    }

    public ChannelState(int num, String state) {
        this.num = num;
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        String format="";
        if(registerTime!=null){

            format = sdf.format(registerTime);
        }

        this.registerTime = format;
    }

    public String getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(Date warnTime) {

        String format="";
        if(warnTime!=null){

            format = sdf.format(warnTime);
        }

        this.warnTime = format;
    }

}

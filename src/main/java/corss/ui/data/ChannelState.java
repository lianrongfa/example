package corss.ui.data;

/**
 * @author lianrongfa
 * @date 2018/7/4
 */
public class ChannelState {

    public static final String CHANNEL_RUNNING="已连接";
    public static final String CHANNEL_CLOSED="已断开";

    public static final int SIZE=2;

    private int num;
    private String state;

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
}

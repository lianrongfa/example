package corss.controller;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import io.netty.channel.Channel;

/**
 * Created by lianrongfa on 2018/6/1.
 */
public abstract class AbstractController implements Controller{

    protected UART info;
    protected String url;
    /**
     * 设备标识
     */
    private String id;

    /**
     * 当前控制器的通道
     */
    private Channel channel;


    public AbstractController(Channel channel, UART info) {
        this.info = info;
        if(channel==null){
            throw new RuntimeException("通道不能为空");
        }
        this.channel=channel;
        this.id = NettyContainer.sourceIds.get(channel);
        this.url= ConfigContext.getInstace().getWebserverUrl();
    }

    public String getId() {
        return id;
    }

    public Channel getChannel() {
        return channel;
    }


    public UART getInfo() {
        return info;
    }

    public void setInfo(UART info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

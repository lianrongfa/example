package corss.controller;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import io.netty.channel.Channel;

/**
 * Created by lianrongfa on 2018/8/30.
 */
public abstract class ConsumerController implements Controller{

    protected UART info;
    protected String url;
    /**
     * 设备标识
     */
    private String id;


    public ConsumerController(UART info) {
        this.info = info;
        this.url= ConfigContext.getInstace().getWebserverUrl();
    }

    public String getId() {
        return id;
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

}

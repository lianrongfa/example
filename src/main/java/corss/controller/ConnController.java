package corss.controller;

import corss.server.NettyContainer;
import io.netty.channel.Channel;

/**
 * Created by lianrongfa on 2018/5/21.
 * 连接控制器
 */
public class ConnController implements Controller {

    /**
     * 设备标识
     */
    private String id;


    public ConnController(String id) {
        this.id = id;
    }

    @Override
    public void close() {
        Object o = NettyContainer.source.get(this.id);
        if(o!=null && o instanceof Channel){
                 Channel channel=(Channel) o;
            remove(channel);
            channel.close().awaitUninterruptibly();
        }

    }

    private void remove(Channel channel){
        NettyContainer.group.remove(channel);
    }
}

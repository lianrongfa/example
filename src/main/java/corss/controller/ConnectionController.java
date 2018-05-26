package corss.controller;

import corss.server.NettyContainer;
import corss.server.protocol.UART;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

/**
 * Created by lianrongfa on 2018/5/21.
 * 连接控制器
 */
public class ConnectionController implements Controller{

    private ChannelHandlerContext ctx;
    private Object info;

    /**
     * 设备标识
     */
    private String id;

    /**
     * 当前控制器的通道
     */
    private Channel channel;


    public ConnectionController(ChannelHandlerContext ctx, UART info) {
        this.ctx = ctx;
        this.info = info;
        /*if(ctx==null)
           throw new RuntimeException("连接通道连接通道出错！");
        this.channel=ctx.channel();*/
    }

    @Override
    public void close() {
        Object o = NettyContainer.source.get(this.id);
        if (o != null && o instanceof Channel) {
            Channel channel = (Channel) o;
            channel.close().awaitUninterruptibly();
            remove(channel);
        }
    }

    @Override
    public Object execute(Object... args) {
        System.out.println("方法执行");
        return null;
    }

    public void test(){
        System.out.println("test");
    }

    private void remove(Channel channel) {
        NettyContainer.group.remove(channel);
    }


    public String getId() {
        return id;
    }

    public Channel getChannel() {
        return channel;
    }
}

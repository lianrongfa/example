package corss.server;

import corss.controller.ConnectionController;
import corss.controller.Controller;
import corss.controller.ProxyController;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.Iterator;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        System.out.println(address+":客户端与服务端连接开始...");
        NettyContainer.group.add(ctx.channel());

    }

    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        NettyContainer.group.remove(channel);

        System.out.println(address+":客户端与服务端连接关闭...");
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("信息接收完毕...");
    }

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 服务端处理客户端请求的核心方法，这里接收了客户端发来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object info) throws Exception {
        System.out.println("服务端接收到：" + info);

        ProxyController proxy = new ProxyController(new ConnectionController(channelHandlerContext,null));
        Controller controller = proxy.getInstace();

        /** 此处可配置 调用哪个行为 **/
        controller.execute();

        //服务端使用这个就能向 每个连接上来的客户端群发消息
        //NettyContainer.group.writeAndFlush(info);
        Iterator<Channel> iterator = NettyContainer.group.iterator();
        /*while (iterator.hasNext()) {
            //打印出所有客户端的远程地址
            Channel channel = iterator.next();
            System.out.println("客户端地址："+channel.remoteAddress());
            channel.writeAndFlush(info);
            System.out.println("给客户端发送："+info);
        }*/
        //单独回复客户端信息
        // channelHandlerContext.writeAndFlush(info);
    }
}

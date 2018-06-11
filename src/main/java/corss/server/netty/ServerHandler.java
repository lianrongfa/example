package corss.server.netty;

import corss.controller.Controller;
import corss.proxy.SimpleFactory;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.send.EquipmentSendUART;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    /**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        System.out.println(address+":客户端与服务端连接开始...");
        NettyContainer.group.add(channel);
        //接通后取设备id

        Thread.sleep(100);
        UART uart = new EquipmentSendUART();
        uart.setType('1');
        uart.parse();
        channel.writeAndFlush(uart.getData());
        Thread.sleep(100);
        channel.writeAndFlush(uart.getData());
        Thread.sleep(100);
        channel.writeAndFlush(uart.getData());
    }

    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress address = channel.remoteAddress();
        NettyContainer.group.remove(channel);

        String id = NettyContainer.sourceIds.get(channel);
        NettyContainer.sourceIds.remove(channel);
        NettyContainer.sourceChannels.remove(id);

        logger.warn(address+":客户端与服务端连接关闭...");
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
        //ctx.close();
    }

    /**
     * 服务端处理客户端请求的核心方法，这里接收了客户端发来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object info) throws Exception {

        UART uart;
        try {
            uart = (UART) info;
        } catch (Exception e) {
            logger.error("协议错误");
            return;
        }
        String id = NettyContainer.sourceIds.get(channelHandlerContext.channel());
        logger.info("服务端接收到设备："+ id +" 的数据："+ uart.toString());
        //回复设备、回复信息为前两位
        byte[] bytes = buildData(uart);
        channelHandlerContext.writeAndFlush(bytes);

        Controller controller = SimpleFactory.createController(channelHandlerContext, uart);
        if(controller!=null){
            controller.executor();
        }else{
            logger.warn("未找到对应的控制器!");
        }
    }

    private byte[] buildData(UART uart) {
        byte[] bytes = new byte[14];
        for (int i=0;i<14;i++){
            bytes[i]=0x20;
        }
        bytes[0]=104;
        bytes[1]=uart.getMark()[0];
        bytes[2]=uart.getMark()[1];
        return bytes;
    }
}

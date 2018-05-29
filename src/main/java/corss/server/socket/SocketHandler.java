package corss.server.socket;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.socket.protocol.SimpleProduct;
import corss.server.socket.protocol.Type;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class SocketHandler extends ChannelInboundHandlerAdapter {
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
        if(info instanceof SimpleProduct){
            SimpleProduct simpleProduct = (SimpleProduct) info;
            int type = simpleProduct.getType();
            String value=new String(simpleProduct.getContent());
            Class clazz = Type.getClazz(type);

            if(clazz!=null&&value!=null){
                Object o = JSONObject.parseObject(value, clazz);
                UART uart = (UART) o;
                uart.parse();
                byte[] data = uart.getData();

                String id = uart.getIdString();

                Channel channel = NettyContainer.sourceChannels.get(id);
                if(channel!=null&&channel.isActive()){
                    ChannelFuture channelFuture = channel.writeAndFlush(data);
                }
            }
        }
        System.out.println("服务端接收到：" + info);
    }
}

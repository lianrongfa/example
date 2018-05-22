package corss.client;

import corss.server.protocol.SimpleProduct;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ClientHandler extends SimpleChannelInboundHandler {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        SimpleProduct product = (SimpleProduct) o;
        System.out.println("从服务端接收:"+new String(product.getContent()));
    }
}
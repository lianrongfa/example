package corss.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import corss.controller.ConnectionController;
import corss.controller.Controller;
import corss.controller.ProxyController;
import corss.server.protocol.SimpleProduct;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.Iterator;

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
            String s=new String(simpleProduct.getContent());
            System.out.println(s);
            JSONObject jsonObject = JSON.parseObject(s);
        }
        System.out.println("服务端接收到：" + info);
    }
}

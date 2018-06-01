package corss.proxy;

import corss.controller.Controller;
import corss.controller.FaultController;
import corss.server.netty.protocol.UART;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lianrongfa on 2018/5/31.
 */
public class SimpleFactory {

    public static Controller createController(ChannelHandlerContext ctx, UART uart){
        ProxyController proxy = new ProxyController(new FaultController(ctx,uart));
        Controller controller = proxy.getInstace();
        return controller;
    }
}

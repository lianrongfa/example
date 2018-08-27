package corss.server.socket;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.socket.protocol.SimpleProduct;
import corss.server.socket.protocol.Type;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class SocketHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
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
        Channel cannel=channelHandlerContext.channel();
        try {
            if(info instanceof SimpleProduct){

                SimpleProduct simpleProduct = (SimpleProduct) info;
                int type = simpleProduct.getType();
                String value=new String(simpleProduct.getContent());
                logger.info("收到web端请求：type="+type+"value:"+value);
                Class clazz = Type.getClazz(type);
                if(clazz!=null&&value!=null){
                    Object o = JSONObject.parseObject(value, clazz);
                    byte[] data = null;
                    String id = null;
                    UART uart = (UART) o;
                    try {
                        uart.parse();
                        data = uart.getData();

                        id = uart.getIdString();
                    } catch (Exception e) {
                        cannel.writeAndFlush("{'state':false,'msg':'协议错误，请检查！'}\r\n");
                        logger.error("web端协议解析错误!",e);
                        return;
                    }
                    if(id==null||"".equals(id)){
                        cannel.writeAndFlush("{'state':false,'msg':'设备id不能为空！'}\r\n");
                        return ;
                    }
                    Channel channel = NettyContainer.sourceChannels.get(id);
                    if(channel!=null&&channel.isActive()){
                        channel.writeAndFlush(data);
                        String s=null;
                        for (byte datum : data) {
                            s=s+datum+" ";
                        }
                        logger.info("服务端向设备："+ id +" 发送数据："+ s);
                        cannel.writeAndFlush("{'state':true,'msg':'操作成功！'}\r\n");
                    }else{
                        cannel.writeAndFlush("{'state':false,'msg':'设备:"+id+" 未在本系统注册！'}\r\n");
                        return;
                    }
                }else{
                    cannel.writeAndFlush("{'state':false,'msg':'没有该类型的操作！'}\r\n");
                }
            }
        } catch (Exception e) {
            cannel.writeAndFlush(e+"\r\n");
            logger.error("web接口调用错误!",e);
        }
    }
}

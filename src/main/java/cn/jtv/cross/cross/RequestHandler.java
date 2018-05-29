package cn.jtv.cross.cross;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import corss.server.netty.NettyContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.OK;

/**
 * Created by lianrongfa on 2018/5/25.
 */
public class RequestHandler extends ChannelInboundHandlerAdapter{
    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;//客户端的请求对象
            JSONObject responseJson = new JSONObject();//新建一个返回消息的Json对象

            //把客户端的请求数据格式化为Json对象
            JSONObject requestJson = null;
            try{
                requestJson = JSON.parseObject(parseJosnRequest(req));
            }catch(Exception e)
            {
                ResponseJson(ctx,req,new String("error json"));
                return;
            }
            String uri = req.uri();//获取客户端的URL

            if (req.method() == HttpMethod.POST) {

            } else {
                //错误处理
                responseJson.put("error", "404 Not Find");
            }

            //向客户端发送结果
            ResponseJson(ctx,req,responseJson.toString());

            NettyContainer.group.writeAndFlush(new byte[]{20,1,8});
        }
    }

    /**
     * 响应HTTP的请求
     * @param ctx
     * @param req
     * @param jsonStr
     */
    private void ResponseJson(ChannelHandlerContext ctx, FullHttpRequest req ,String jsonStr)
    {

        boolean keepAlive = HttpUtil.isKeepAlive(req);
        byte[] jsonByteByte = jsonStr.getBytes();
        System.out.println("json byte NO. is "+jsonByteByte.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(jsonByteByte));
        response.headers().set(CONTENT_TYPE, "text/json");
        System.out.println("conten byte NO. is "+response.content().readableBytes());
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 获取请求的内容
     * @param request
     * @return
     */
    private String parseJosnRequest(FullHttpRequest request) {
        ByteBuf jsonBuf = request.content();
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        return jsonStr;
    }

}

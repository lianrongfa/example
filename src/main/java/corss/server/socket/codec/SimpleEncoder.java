package corss.server.socket.codec;

import corss.server.socket.protocol.SimpleProduct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by lianrongfa on 2018/5/18.
 * 自定义协议编码器
 */
public class SimpleEncoder extends MessageToByteEncoder<SimpleProduct> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SimpleProduct msg, ByteBuf out) throws Exception {
        if(msg!=null){
            // 写入消息的开头的信息标志(int类型)
            out.writeInt(msg.getHead_data());
            // 写入消息的长度(int 类型)
            out.writeInt(msg.getContentLength());
            // 写入消息的内容(byte[]类型)
            out.writeBytes(msg.getContent());
        }
    }
}

package corss.server.socket.codec;

import corss.server.socket.protocol.ConstantValue;
import corss.server.socket.protocol.SimpleProduct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by lianrongfa on 2018/5/18.
 * 简单协议的解码器
 */
public class SimpleDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        if(buffer.readableBytes()>=ConstantValue.BASE_LENGTH){

            //防止消息过大,客户端攻击
            if (buffer.readableBytes() > ConstantValue.MAX_LENGTH) {
                buffer.skipBytes(buffer.readableBytes());
                return;
            }

            int startReader;

            while (true){
                startReader=buffer.readerIndex();
                buffer.markReaderIndex();
                if(buffer.readInt()==ConstantValue.HEAD_DATA){
                    break;
                }

                buffer.resetReaderIndex();
                buffer.readByte();

                if(buffer.readableBytes()<ConstantValue.BASE_LENGTH){
                    return;
                }
            }

            int type=buffer.readInt();
            int length=buffer.readInt();

            if(buffer.readableBytes()<length){
                // 还原读指针,等待数据到齐
                buffer.readerIndex(startReader);
                return;
            }

            byte [] data =new byte[length];
            buffer.readBytes(data);

            SimpleProduct simpleProduct = new SimpleProduct(length, data, type);

            out.add(simpleProduct);

        }else{
            String s="协议错误!";
            SimpleProduct simpleProduct = new SimpleProduct(s.length(), s.getBytes(), (byte) 1);
            ctx.writeAndFlush(simpleProduct);
        }
    }
}

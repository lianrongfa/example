package corss.server.codec;

import corss.server.protocol.ConstantValue;
import corss.server.protocol.SimpleProduct;
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

            int length=buffer.readInt();
            //int length2=buffer.readInt();

            if(buffer.readableBytes()<length){
                // 还原读指针,等待数据到齐
                buffer.readerIndex(startReader);
                return;
            }

            byte [] data =new byte[length];
            buffer.readBytes(data);

            SimpleProduct simpleProduct = new SimpleProduct(length, data);

            out.add(simpleProduct);

        }else{
            System.out.println("信息包不足!");
            System.out.println(buffer.toString());
        }
    }
}

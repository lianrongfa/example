package corss.server.netty.codec;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.*;
import corss.server.socket.protocol.ConstantValue;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by lianrongfa on 2018/5/18.
 * 简单协议的解码器
 */
public class UARTDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        if (buffer.readableBytes() >= AbstractUART.MIN_LENGTH) {

            //防止消息过大,客户端攻击
            /*if (buffer.readableBytes() > AbstractUART.MAX_LENGTH) {
                buffer.skipBytes(buffer.readableBytes());
                return;
            }*/

            int startReader;

            ConfigContext instace = ConfigContext.getInstace();

            byte mark;
            while (true) {
                startReader = buffer.readerIndex();
                buffer.markReaderIndex();

                mark = buffer.readByte();
                if (isSupport(instace, mark)) {
                    break;
                }

                buffer.resetReaderIndex();
                buffer.readByte();

                if (buffer.readableBytes() < AbstractUART.MIN_LENGTH) {
                    return;
                }
            }

            int length = instace.getProtocolConfig().getLength(mark);

            buffer.readerIndex(startReader);
            if (buffer.readableBytes() < length) {
                // 等待数据到齐
                return;
            }

            byte[] data = new byte[length];
            buffer.readBytes(data);


            Channel channel = ctx.channel();

            String id = NettyContainer.getId(channel);

            Object uart = ProtocolFactory.createUART(mark, data,id);


            out.add(uart);

        }
    }

    /**
     * 判断是否支持协议
     *
     * @param instace
     * @param mark
     * @return
     */
    private boolean isSupport(ConfigContext instace, byte mark) {
        return instace.getProtocolConfig().getReceiveContainer().contains(mark);
    }
}

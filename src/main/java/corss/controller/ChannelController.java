package corss.controller;

import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.EquipmentRecUART;
import corss.ui.data.ChannelState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/6/5.
 * 设备id与通道关系控制器
 */
public class ChannelController extends AbstractController {

    private static final Logger logger= LoggerFactory.getLogger(ChannelController.class);

    public ChannelController(ChannelHandlerContext ctx, UART info) {
        super(ctx, info);
    }

    @Override
    public String executor() {

        if(this.info instanceof EquipmentRecUART) {
            String idString = this.info.getIdString();
            Channel c = this.getChannel();
            Map<String, Channel> sourceChannels = NettyContainer.sourceChannels;
            Map<Channel, String> sourceIds = NettyContainer.sourceIds;
            if(idString!=null&&c!=null){
                Channel channel = sourceChannels.get(idString);
                if (channel != null&&!channel.equals(c)) {
                    logger.warn("设备：" + idString + " 已与通道关联，已将此前设备通道关闭，请检查设备id是否重复！");
                    channel.close();
                }
                sourceChannels.put(idString,c);
                sourceIds.put(c,idString);

                //ui data
                //buildUiData(idString);

                NettyContainer.warnSet.remove(idString);

                logger.info("设备："+idString+" 与通道关联成功.");
            }
        }
        return null;
    }

    private void buildUiData(String idString) {
        ChannelState channelState = NettyContainer.uiData.get(idString);
        if(channelState==null){
            channelState = new ChannelState(Integer.parseInt(idString), ChannelState.CHANNEL_RUNNING);
        }

        channelState.setRegisterTime(new Date());
    }
}

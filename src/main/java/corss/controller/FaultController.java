package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.configuration.ConfigContext;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.FaultUART;
import corss.util.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lianrongfa on 2018/5/21.
 * 故障发生控制器
 */
public class FaultController implements Controller{

    private ChannelHandlerContext ctx;
    private UART info;

    /**
     * 设备标识
     */
    private String id;

    /**
     * 当前控制器的通道
     */
    private Channel channel;


    public FaultController(ChannelHandlerContext ctx, UART info) {
        this.ctx = ctx;
        this.info = info;
        if(ctx==null)
           throw new RuntimeException("连接通道出错！");
        this.channel=ctx.channel();
        this.id = NettyContainer.sourceIds.get(ctx.channel());
    }

    public String getId() {
        return id;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public String executor() {
        if(info instanceof FaultUART){
            FaultUART faultUART = (FaultUART) this.info;

            String url = ConfigContext.getInstace().getWebserverUrl();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("proEquipmentNum",getId());
            jsonObject.put("proType",this.info.getMarkString());
            jsonObject.put("eq26",faultUART.getFaultTime());

            String msg = jsonObject.toJSONString();

            return HttpUtil.httpRequest(url,"POST",msg);
        }

        return null;
    }
}

package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.send.SendSeerUART;
import corss.util.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lianrongfa on 2018/6/4.
 * 转发预告信息
 */
public class ForwardController extends AbstractController {

    private static final Logger logger= LoggerFactory.getLogger(Controller.class);


    private final static String method = "/astassetconfig.do?method=findNextAssetId";

    public ForwardController(ChannelHandlerContext ctx, UART info) {
        super(ctx, info);
    }

    @Override
    public String executor() {

        byte b = info.getMark()[1];

        String runType = null;
        if (b == '0') {
            runType="上行";
        } else if (b == '1') {
            runType="下行";
        }

        //jsonObject.put("assetId",getId());
        String param="assetId="+getId();
        param=param+"&runType="+runType;

        String id = HttpUtil.httpRequest(getUrl() + method, "POST", param);

        Channel channel = NettyContainer.sourceChannels.get(id);

        SendSeerUART seerUART = new SendSeerUART();
        seerUART.setType((char) b);
        seerUART.parse();

        if(channel==null){
            logger.warn("设备："+id+"未在本系统注册！");
        }else{
            channel.writeAndFlush(seerUART.getData());
        }

        return null;
    }
}

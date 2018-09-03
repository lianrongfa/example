package corss.controller;

import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.send.SendSeerUART;
import corss.util.HttpUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lianrongfa on 2018/6/4.
 * 转发预告信息
 */
public class ForwardController extends ConsumerController {

    private static final Logger logger= LoggerFactory.getLogger(Controller.class);


    private final static String method = "/astassetconfig.do?method=findNextAssetId";

    public ForwardController(Channel channel, UART info) {
        super(info);
    }

    @Override
    public String executor() throws Exception{

        byte b = info.getMarks()[1];

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

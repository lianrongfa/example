package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.FaultUART;
import corss.server.netty.protocol.receive.RemoteSettingRecUART;
import corss.util.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

/**
 * Created by lianrongfa on 2018/5/21.
 * 设备参数接入
 */
public class SettingController extends ConsumerController {

    private final static String method = "/astassetconfig.do?method=saveAstConfig";

    public SettingController(UART info) {
        super(info);
    }

    @Override
    public String executor() throws Exception {
        if (info instanceof RemoteSettingRecUART) {
            JSONObject o = (JSONObject)JSONObject.toJSON(this.info);
            o.put("assetId",getId());
            //o.put("assetId","1");
            String msg = o.toJSONString();
            msg="configJson="+msg;
            String result = HttpUtil.httpRequest(getUrl() + method, "POST", msg);
            return result;
        }

        return null;
    }
}

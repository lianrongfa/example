package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.FaultUART;
import corss.server.netty.protocol.receive.RemoteSettingRecUART;
import corss.util.HttpUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lianrongfa on 2018/5/21.
 * 设备参数接入
 */
public class SettingController extends AbstractController {

    private final static String method = "/astassetconfig.do?method=saveAstConfig";

    public SettingController(ChannelHandlerContext ctx, UART info) {
        super(ctx, info);
    }

    @Override
    public String executor() {
        if (info instanceof RemoteSettingRecUART) {
            RemoteSettingRecUART faultUART = (RemoteSettingRecUART) this.info;

            JSONObject o = (JSONObject)JSONObject.toJSON(faultUART);
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

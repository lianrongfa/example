package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.RemoteSettingRecUART;
import corss.server.netty.protocol.receive.WorkUserUART;
import corss.util.HttpUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lianrongfa on 2018/6/21.
 * 同步岗位人员
 */
public class WorkUserController extends AbstractController {

    private final static String method = "/astassetconfig.do?method=syncUserConfig";

    public WorkUserController(ChannelHandlerContext ctx, UART info) {
        super(ctx, info);
    }

    @Override
    public String executor() {
        if (info instanceof WorkUserUART) {
            WorkUserUART workUserUART = (WorkUserUART) this.info;
            workUserUART.setAssetId(getId());
            String msg = JSONObject.toJSONString(workUserUART);
            msg="userJson="+msg;
            String result = HttpUtil.httpRequest(getUrl() + method, "POST", msg);
            return result;
        }
        return null;
    }
}

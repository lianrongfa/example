package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.FaultUART;
import corss.util.HttpUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lianrongfa on 2018/5/21.
 * 故障发生控制器
 */
public class FaultController extends AbstractController{

    private final static String method="/dkProblem.do?method=saveProblem";

    public FaultController(ChannelHandlerContext ctx, UART info) {
        super(ctx, info);
    }

    @Override
    public String executor() {
        if(info instanceof FaultUART){
            FaultUART faultUART = (FaultUART) this.info;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("proEquipmentNum",getId());
            jsonObject.put("proType",this.info.getMarkString());
            jsonObject.put("eq26",faultUART.getFaultTime());

            String msg = jsonObject.toJSONString();
            msg="faultJson="+msg;

            String result = HttpUtil.httpRequest(getUrl() + method, "POST", msg);

            return result;
        }

        return null;
    }
}

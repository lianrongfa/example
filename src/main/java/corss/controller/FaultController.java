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
            jsonObject.put("proEquipmentNum",getId());//设备id
            jsonObject.put("proType",this.info.getMarkString());//故障类型
            jsonObject.put("eq26",faultUART.getFaultTime());//故障时间

            if(faultUART.getData().length==24){
                jsonObject.put("eq1",faultUART.getEq1());//超时时长
                jsonObject.put("checkPreson",faultUART.getCheckPreson());//道口主人员
                jsonObject.put("reviewPerson",faultUART.getReviewPerson());//道口副人员
            }

            String msg = jsonObject.toJSONString();
            msg="faultJson="+msg;

            String result = HttpUtil.httpRequest(getUrl() + method, "POST", msg);

            return result;
        }

        return null;
    }
}

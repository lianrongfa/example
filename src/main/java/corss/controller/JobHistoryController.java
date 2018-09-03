package corss.controller;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.receive.JobHistoryUART;
import corss.server.netty.protocol.receive.WorkUserUART;
import corss.util.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by lianrongfa on 2018/6/21.
 * 详细作业记录
 */
public class JobHistoryController extends ConsumerController {

    private final static String method = "/astassetconfig.do?method=syncDetailRecord";

    public JobHistoryController(UART info) {
        super(info);
    }

    @Override
    public String executor() throws Exception {
        if (info instanceof JobHistoryUART) {
            try {
                JSONObject o = (JSONObject) JSONObject.toJSON(this.info);
                o.put("runType", new String(new byte[]{this.info.getData()[1]}, "US-ASCII"));
                o.put("assetId", getId());
                String msg = "detailJson=" + o.toJSONString();
                String result = HttpUtil.httpRequest(getUrl() + method, "POST", msg);
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

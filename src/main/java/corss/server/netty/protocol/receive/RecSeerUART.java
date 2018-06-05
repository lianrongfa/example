package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;

/**
 * Created by lianrongfa on 2018/6/2.
 *
 * 转发数据
 * 本记录仪向临近道口发预告信息(即为接收然后转发)
 */
public class RecSeerUART extends AbstractUART {

    public RecSeerUART() {
    }

    @Override
    public void parse() {
        this.mark=data[0];
        this.type= (char) data[1];
    }
}

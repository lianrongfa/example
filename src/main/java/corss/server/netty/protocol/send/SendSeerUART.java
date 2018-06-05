package corss.server.netty.protocol.send;

import corss.server.netty.protocol.AbstractUART;

/**
 * Created by lianrongfa on 2018/6/2.
 *
 * 转发数据
 * 服务向临近道口发预告信息(即为接收然后转发)
 */
public class SendSeerUART extends AbstractUART {

    public SendSeerUART() {
        super((byte) 102,'0');
    }

    @Override
    public void parse() {
        this.data[1]= (byte) this.type;
    }
}

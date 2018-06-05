package corss.server.netty.protocol.send;

import corss.server.netty.protocol.AbstractUART;

/**
 * Created by lianrongfa on 2018/5/28.
 * <p>
 * 参数调取 size 14Byte
 */
public class PullSettingUART extends AbstractUART {
    public PullSettingUART() {
        super((byte) 97, '0');
    }

    @Override
    public void parse() {

    }

}

package corss.server.netty.protocol.send;

import corss.server.netty.protocol.AbstractUART;

/**
 * Created by lianrongfa on 2018/5/28.
 * <p>
 * 参数调取 size 2Byte
 */
public class PullSettingUART extends AbstractUART {
    public PullSettingUART() {
        super((byte) 100, (byte) 0x30);
    }

    @Override
    public void parse() {

    }

}

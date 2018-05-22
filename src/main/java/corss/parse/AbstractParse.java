package corss.parse;

import java.nio.channels.SocketChannel;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public abstract class AbstractParse<T,M> implements Parse<T,M>{
    protected SocketChannel channel;
    protected Class clazz;

    protected AbstractParse(SocketChannel channel,Class<T> clazz) {
        this.channel = channel;
        this.clazz=clazz;
    }




    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}

package corss.util;

import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.send.EquipmentSendUART;
import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lianrongfa on 2018/6/13.
 */
public class PullEquipmentId implements Runnable {

    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    private final byte [] uart=new byte[]{100,0x31,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20};
    @Override
    public void run() {
        //取设备id
        for (Channel channel : NettyContainer.group) {
            String s = NettyContainer.sourceIds.get(channel);
            if (s == null || "".equals(s)) {
                if (channel.isActive()) {
                    channel.writeAndFlush(uart);
                }

            }
        }
    }

    public void executor() {
        scheduledThreadPool.scheduleWithFixedDelay(this, 60000, 10000, TimeUnit.MILLISECONDS);
    }
}

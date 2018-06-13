package corss.util;

import corss.server.netty.NettyContainer;
import corss.server.netty.protocol.UART;
import corss.server.netty.protocol.send.EquipmentSendUART;
import io.netty.channel.Channel;

import java.util.concurrent.TimeUnit;

/**
 * Created by lianrongfa on 2018/6/13.
 */
public class PullEquipmentId extends Thread {

    private volatile boolean isRunning = true;

    @Override
    public void run() {
        try {
            //等服务器启动2分钟后取设备id
            Thread.sleep(TimeUnit.MINUTES.toMillis(2));
            //构建协议
            UART uart = new EquipmentSendUART();
            uart.parse();
            //取设备id
            while (isRunning) {
                Thread.sleep(5000);
                for (Channel channel : NettyContainer.group) {
                    String s = NettyContainer.sourceIds.get(channel);
                    if (s == null || "".equals(s)) {
                        if (channel.isActive()) {
                            channel.writeAndFlush(uart.getData());
                        }

                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

package corss.controller;

import corss.server.netty.protocol.UART;

import java.io.IOException;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public interface Controller{
    String executor() throws Exception;;

    UART getInfo();
}

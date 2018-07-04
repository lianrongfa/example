package corss.server;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyServer;
import corss.server.socket.SocketServer;
import corss.ui.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ServerStart {

    private static final Logger logger = LoggerFactory.getLogger(ServerStart.class);

    public static void main(String[] args){
        try {
            ConfigContext instace = ConfigContext.getInstace();
            //netty
            NettyServer server = new NettyServer(instace.getServerPort());
            //web
            SocketServer socketServer = new SocketServer(instace.getSocketPort());
            //ui
            instace.setMonitor(new Monitor());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("server start error.. ");
        }
    }
}

package corss.server;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyServer;
import corss.server.socket.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ServerStart {

    private static final Logger logger = LoggerFactory.getLogger(ServerStart.class);

    public static void main(String[] args){
        try {
            NettyServer server = new NettyServer(ConfigContext.getInstace().getServerPort());
            SocketServer socketServer = new SocketServer(ConfigContext.getInstace().getSocketPort());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("server start error.. ");
        }
    }
}

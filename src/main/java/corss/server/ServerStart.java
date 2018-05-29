package corss.server;

import corss.configuration.ConfigContext;
import corss.server.netty.NettyServer;
import corss.server.socket.SocketServer;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ServerStart {



    public static void main(String[] args){
        try {
            NettyServer server = new NettyServer(ConfigContext.getInstace().getServerPort());
            SocketServer socketServer = new SocketServer(8089);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

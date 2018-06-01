package corss.server.socket;

import corss.configuration.ConfigContext;
import corss.server.socket.codec.SimpleDecoder;
import corss.server.socket.codec.SimpleEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lianrongfa on 2018/5/25.
 */
public class SocketServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private int port;

    public SocketServer(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    private void bind() throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(boss,work);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG,1024*3);
        bootstrap.childOption(ChannelOption.TCP_NODELAY,true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                ChannelPipeline pipeline = sc.pipeline();
                pipeline.addLast(new SimpleDecoder());
                pipeline.addLast(new SimpleEncoder());
                pipeline.addLast(new SocketHandler());
                /*pipeline.addLast(new HttpServerCodec());
                pipeline.addLast(new HttpObjectAggregator(65535));
                pipeline.addLast(new RequestHandler());*/
            }
        });

        ChannelFuture future = bootstrap.bind(this.port).sync();

        if (future.isSuccess()) {
            logger.info("socket server started success port:"+ ConfigContext.getInstace().getSocketPort());
        } else {
            logger.error("socket server started fail");
        }
    }
}

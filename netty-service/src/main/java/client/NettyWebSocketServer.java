package client;

import handler.NettyWebsocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * netty websocket服务
 *
 * @author barnak
 */
public class NettyWebSocketServer {

    public static void init() {
        System.out.println("正在启动websocket服务器");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline()
                            .addLast("http-codec", new HttpServerCodec() )
                            .addLast("aggregator", new HttpObjectAggregator(65536) )
                            .addLast("http-chunked", new ChunkedWriteHandler() )
                            .addLast("handler", new NettyWebsocketHandler() );
                }
            });
            Channel channel = bootstrap.bind(8888).sync().channel();
            System.out.println("webSocket服务器启动成功："+channel);
            System.out.println(NettyWebSocketServer.class + " 启动正在监听： " + channel.localAddress());
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("运行出错："+e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.out.println("websocket服务器已关闭");
        }
    }

}

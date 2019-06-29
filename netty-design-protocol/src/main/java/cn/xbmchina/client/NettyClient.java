package cn.xbmchina.client;

import cn.xbmchina.coder.SmartCarDecoder;
import cn.xbmchina.coder.SmartCarEncoder;
import cn.xbmchina.coder.UavEncoder;
import cn.xbmchina.handler.ClientHandler;
import cn.xbmchina.handler.ClientInitHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * 客户端
 * 1.为初始化客户端，创建一个Bootstrap实例
 * 2.为进行事件处理分配了一个NioEventLoopGroup实例，其中事件处理包括创建新的连接以及处理入站和出站数据；
 * 3.当连接被建立时，一个EchoClientHandler实例会被安装到（该Channel的一个ChannelPipeline中；
 * 4.在一切都设置完成后，调用Bootstrap.connect()方法连接到远程节点。
 */
public class NettyClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new UavEncoder());
                    ch.pipeline().addLast(new SmartCarEncoder());
                    ch.pipeline().addLast(new SmartCarDecoder());
//                    ch.pipeline().addLast(new ClientInitHandler());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.connect("127.0.0.1", 8000);
    }
}

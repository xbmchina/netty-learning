package cn.xbmchina.rpc.server;

import cn.xbmchina.rpc.common.Const;
import cn.xbmchina.rpc.zk.ZkFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Component
public class RPCServer implements ApplicationListener<ContextRefreshedEvent> {


    public void start() throws Exception {

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            bootstrap.group(parentGroup, childGroup)
                    .localAddress(new InetSocketAddress(Const.SERVER_HOST, Const.SERVER_PORT))
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535,Delimiters.lineDelimiter()[0]));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new IdleStateHandler(60, 20, 10, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new SimpleServerHandler());
                        }
                    });


            ChannelFuture f = bootstrap.bind().sync();
            CuratorFramework client = ZkFactory.create();

            int weight = 1;
            client.create()
                    //临时节点
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(Const.RPC_ZK_SERVER_PATH + InetAddress.getLocalHost().getHostAddress()+"#"+Const.SERVER_PORT+"#"+weight+"#");

            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully().sync();
            childGroup.shutdownGracefully().sync();
        }

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务端启动异常！！");
        }
    }
}

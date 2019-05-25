package cn.xbmchina.rpc.core;

import cn.xbmchina.rpc.common.Response;
import cn.xbmchina.rpc.entity.ClientRequest;
import cn.xbmchina.rpc.handler.SimpleClientHandler;
import cn.xbmchina.rpc.zk.ZkFactory;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RPCClient {

    static final Bootstrap bootstrap = new Bootstrap();
    static ChannelFuture f;
//    public static Set<String> realServerPath = new HashSet<>();

    static {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535,Delimiters.lineDelimiter()[0]));
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new SimpleClientHandler());
                        }
                    });

            String host = "127.0.0.1";
            int port = 8080;
            CuratorFramework client = ZkFactory.create();

            List<String> serverPaths = client.getChildren().forPath("/netty");
            CuratorWatcher watcher = new ServerWatcher();
            client.getChildren().usingWatcher(watcher).forPath("/netty");
            for (String serverPath : serverPaths) {
                String[] str = serverPath.split("#");
                int weight = Integer.valueOf(str[2]);
                if (weight > 0) {
                    for (int i = 0; i < weight; i++) {
                        ChannelManager.realServerPath.add(str[0] + "#" + str[1]);
                        ChannelFuture channelFuture = RPCClient.bootstrap.connect(str[0], Integer.valueOf(str[1]));
                        ChannelManager.addChannel(channelFuture);
                    }
                }
            }

            if (ChannelManager.realServerPath.size() > 0) {

                String[] hostAndPort = ChannelManager.realServerPath.toArray()[0].toString().split("#");
                host = hostAndPort[0];
                port = Integer.valueOf(hostAndPort[1]);
            }

//            f = bootstrap.connect(new InetSocketAddress(host, port)).sync();
//            f.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                group.shutdownGracefully().sync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }


    }


    public static Response send(ClientRequest request) {
        f = ChannelManager.get(ChannelManager.position);
        f.channel().writeAndFlush(JSONObject.toJSONString(request));
        DefaultFuture df = new DefaultFuture(request);
        return df.get();
    }


}

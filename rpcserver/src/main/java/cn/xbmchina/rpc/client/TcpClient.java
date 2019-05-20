package cn.xbmchina.rpc.client;

import cn.xbmchina.rpc.client.entity.ClientRequest;
import cn.xbmchina.rpc.client.entity.Response;
import cn.xbmchina.rpc.common.Const;
import cn.xbmchina.rpc.server.entity.ServerRequest;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class TcpClient {

    static final Bootstrap bootstrap = new Bootstrap();
    static ChannelFuture f;

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

            f = bootstrap.connect(new InetSocketAddress(Const.SERVER_HOST, Const.SERVER_PORT)).sync();
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

    public static Response send(ClientRequest request){
        f.channel().writeAndFlush(JSONObject.toJSONString(request));
        DefaultFuture df = new DefaultFuture(request);
        return df.get();
    }




}

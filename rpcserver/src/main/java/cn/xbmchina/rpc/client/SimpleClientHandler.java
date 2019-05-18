package cn.xbmchina.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private int curTime = 0;
    private int beatTime = 3;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("===SimpleClientHandler==channelRead===");
        System.out.println(msg.toString());
    }



    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("客户端循环心跳监测发送: "+new Date());

        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.WRITER_IDLE){
                if (curTime<beatTime) {
                    curTime++;
                    ctx.writeAndFlush("ping");
                }
            }
        }
    }


}

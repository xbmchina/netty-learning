package cn.xbmchina.heartbeat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

public class ReconnectClientHandler extends ChannelInboundHandlerAdapter {

    private int curTime = 0;
    private int beatTime = 3;

    private ReconnectClient client;
    public ReconnectClientHandler(ReconnectClient client) {
        this.client = client;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("ReconnectClientHandler客户端循环心跳监测发送: "+new Date());

        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.WRITER_IDLE){
                if (curTime<beatTime) {
                    curTime++;
                    ctx.writeAndFlush("ReconnectClientHandler=biubiu.....");
                }
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.doConnect();
        System.out.println("重新連接了呀。。。。");
    }
}

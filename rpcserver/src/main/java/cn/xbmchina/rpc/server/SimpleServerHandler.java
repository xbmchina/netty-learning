package cn.xbmchina.rpc.server;

import cn.xbmchina.rpc.client.entity.Response;
import cn.xbmchina.rpc.server.entity.ServerRequest;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    private int lossConnectCount = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client says: "+msg.toString());
        lossConnectCount = 0;

        ServerRequest request = JSONObject.parseObject(msg.toString(), ServerRequest.class);
        Response response = new Response();
        response.setId(request.getId());
        response.setResult("is Ok");
        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));

    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("===exceptionCaught===");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("已经5秒未收到客户端的消息了！");
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                //读空闲
                System.out.println("==读空闲==");
                lossConnectCount++;
                if (lossConnectCount >2) {
                    System.out.println("关闭这个不活跃通道！");
                    ctx.channel().close();
                }
            }else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("==写空闲==");
//                ctx.channel().writeAndFlush("pong");
            }else if (event.state().equals(IdleState.ALL_IDLE)) {
//                ctx.channel().writeAndFlush("pong");
            }


        }else {
            super.userEventTriggered(ctx,evt);
        }
    }




}

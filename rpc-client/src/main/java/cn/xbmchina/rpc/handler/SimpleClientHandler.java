package cn.xbmchina.rpc.handler;

import cn.xbmchina.rpc.common.DefaultFuture;
import cn.xbmchina.rpc.common.Response;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleClientHandler extends SimpleChannelInboundHandler<Object> {



    /**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ClientRequest request = new ClientRequest();
//        request.setContent("客户端连上了！！！");
//        ctx.channel().writeAndFlush(JSONObject.toJSONString(request));
    }

    /**
     * 当从服务器接收到一个消息时被调用
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object byteBuf) throws Exception {
        System.out.println("客户端读取到数据了！！！");
        System.out.println(byteBuf.toString());

        DefaultFuture.receive( JSONObject.parseObject(byteBuf.toString(), Response.class));
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

package cn.xbmchina.handler;

import cn.xbmchina.entity.UavEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HelloClientIntHandler.channelActive");
        UavEntity ua = new UavEntity();
        ua.setName("四翼无人机Plus");
        ua.setId(UUID.randomUUID().toString());
        ua.setBrand("大疆");
        ctx.writeAndFlush(ua);
    }
}
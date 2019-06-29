package cn.xbmchina.coder;

import cn.xbmchina.converter.ByteObjConverter;
import cn.xbmchina.entity.UavEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class UavEncoder extends MessageToByteEncoder<UavEntity> {

    @Override
    protected void encode(ChannelHandlerContext ctx, UavEntity msg, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.ObjectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}

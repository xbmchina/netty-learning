package cn.xbmchina.coder;

import cn.xbmchina.converter.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class UavDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        Object obj = ByteObjConverter.ByteToObject(bytes);
        out.add(obj);
    }

}

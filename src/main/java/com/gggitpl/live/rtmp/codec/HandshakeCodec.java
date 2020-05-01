package com.gggitpl.live.rtmp.codec;

import com.gggitpl.live.rtmp.Handshake;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gggitpl
 */
@Slf4j
public class HandshakeCodec extends ByteToMessageCodec<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (Handshake.isC0C1(in)) {
            out.add(Handshake.ofC0C1(in));
        } else if (Handshake.isC2(in)) {
            out.add(Handshake.ofC2(in));
        }
    }
}

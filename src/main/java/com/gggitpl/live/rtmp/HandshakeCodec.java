package com.gggitpl.live.rtmp;

import com.gggitpl.live.rtmp.message.Handshake;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @author gggitpl
 */
public class HandshakeCodec extends ByteToMessageCodec<Handshake> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Handshake msg, ByteBuf out) {
        out.writeBytes(msg.getBytes());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (Handshake.isC0C1(in)) {
            out.add(Handshake.ofC0C1(in));
        }
    }
}

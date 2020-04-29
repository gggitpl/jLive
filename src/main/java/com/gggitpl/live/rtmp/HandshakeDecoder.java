package com.gggitpl.live.rtmp;

import com.gggitpl.live.rtmp.message.Handshake;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gggitpl
 */
@Slf4j
public class HandshakeDecoder extends FixedLengthFrameDecoder {
    public static final int LEN = 1537;

    public HandshakeDecoder() {
        super(LEN);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        if (in.readableBytes() == LEN) {
            if (Handshake.isC0(in)) {
                return Handshake.of(in);
            }
        }
        return null;
    }

}

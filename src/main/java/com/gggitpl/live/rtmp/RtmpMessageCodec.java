package com.gggitpl.live.rtmp;

import com.gggitpl.live.rtmp.message.RtmpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gggitpl
 */
@Slf4j
public class RtmpMessageCodec extends ByteToMessageCodec<RtmpMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RtmpMessage msg, ByteBuf out) {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        log.debug("{}", in);
    }
}

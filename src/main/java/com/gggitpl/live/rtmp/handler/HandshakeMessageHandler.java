package com.gggitpl.live.rtmp.handler;

import com.gggitpl.live.rtmp.message.Handshake;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gggitpl
 */
@Slf4j
public class HandshakeMessageHandler extends MessageToMessageDecoder<Handshake> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Handshake msg, List<Object> out) {
        log.debug("{}", msg);
        ctx.writeAndFlush(msg);
    }
}

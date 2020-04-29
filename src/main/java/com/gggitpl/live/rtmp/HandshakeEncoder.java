package com.gggitpl.live.rtmp;

import com.gggitpl.live.rtmp.message.Handshake;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gggitpl
 */
@Slf4j
public class HandshakeEncoder extends MessageToMessageEncoder<Handshake> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Handshake msg, List<Object> out) {
        log.debug("Handshake [{}]", msg);
        out.add(msg);
    }
}

package com.gggitpl.live.rtmp.handler;

import com.gggitpl.live.rtmp.Handshake;
import com.gggitpl.live.rtmp.codec.HandshakeCodec;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gggitpl
 */
@Slf4j
public class HandshakeHandler extends SimpleChannelInboundHandler<Handshake> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Handshake msg) {
        log.debug("Handshake: [{}]", msg);
        if (msg.isC0C1()) {
            ctx.writeAndFlush(msg.getS0S1(ctx));
        } else if (msg.isC2()) {
            ctx.writeAndFlush(msg.getS2(ctx)).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    ctx.pipeline().remove(HandshakeCodec.class);
                    ctx.pipeline().remove(HandshakeHandler.class);
                    ctx.pipeline().addLast(new RtmpHandler());
                }
            });
        }
    }
}

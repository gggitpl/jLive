package com.gggitpl.live.rtmp;

import com.gggitpl.live.rtmp.message.Handshake;
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
        log.debug("接收到握手消息: [{}]", msg);
        if (msg.isC0C1()) {
            ctx.writeAndFlush(msg.buildS0S1S2()).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    ctx.pipeline().remove(HandshakeCodec.class);
                    ctx.pipeline().remove(HandshakeHandler.class);
                    log.debug("握手成功后删除Handshake相关codec/handler [{}]", ctx.pipeline());
                    ctx.pipeline().addLast(new RtmpMessageCodec());
                }
            });
        }
    }
}

package com.gggitpl.live.rtmp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoCloseChannelHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        log.debug("未正确处理的消息: {}", msg);
        log.debug("{}", ctx.pipeline());
        ctx.close();
    }
}

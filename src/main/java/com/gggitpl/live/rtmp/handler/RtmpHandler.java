package com.gggitpl.live.rtmp.handler;

import com.gggitpl.live.rtmp.BasicHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gggitpl
 */
@Slf4j
public class RtmpHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        log.debug("{}", in);
        final BasicHeader of = BasicHeader.of(in);
        System.out.println(of);

        ctx.close();
    }
}

package com.gggitpl.live.rtmp.handler;

import com.gggitpl.live.rtmp.ChunkHeader;
import com.gggitpl.live.rtmp.SetChunkSize;
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
        if (in.readableBytes() < SetChunkSize.MAXIMUM_CHUNK_SIZE) {
            return;
        }
        final ChunkHeader chunkHeader = ChunkHeader.of(in);
        log.debug("chunkHeader: {}", chunkHeader);
        if (SetChunkSize.isSetChunkSize(chunkHeader)) {
            final SetChunkSize setChunkSize = SetChunkSize.of(chunkHeader, in);
            System.out.println(setChunkSize);
        }
        //ctx.close();
    }
}

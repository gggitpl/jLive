package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import lombok.Builder;
import lombok.Data;

/**
 * @author gggitpl
 */
@Data
@Builder
public class Chunk {

    private final ChunkHeader chunkHeader;

    private final ByteBuf chunkData;

    public static Chunk of(ByteBuf in) {
        final ChunkHeader chunkHeader = ChunkHeader.of(in);
        return Chunk
                .builder()
                .chunkHeader(chunkHeader)
                .chunkData(in.readBytes(chunkHeader.getMessageHeader().getLength()))
                .build();
    }
}

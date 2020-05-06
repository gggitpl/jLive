package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author gggitpl
 */
@Data
public class Chunk {

    private final BasicHeader basicHeader;

    private final MessageHeader messageHeader;

    private final Long extendedTimestamp;

    private final ByteBuf chunkData;

}

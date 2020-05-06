package com.gggitpl.live.rtmp;

import lombok.Data;

@Data
public abstract class RtmpMessage {

    private final ChunkHeader chunkHeader;

}

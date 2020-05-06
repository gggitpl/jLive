package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import lombok.ToString;

@ToString(callSuper = true)
public class SetChunkSize extends RtmpMessage {

    public static final int MAXIMUM_CHUNK_SIZE = 128;

    private final long chunkSize;

    public SetChunkSize(ChunkHeader chunkHeader, ByteBuf in) {
        super(chunkHeader);
        this.chunkSize = in.readBytes(chunkHeader.getMessageHeader().getLength()).readUnsignedInt();
    }

    public static SetChunkSize of(ChunkHeader chunkHeader, ByteBuf in) {
        return new SetChunkSize(chunkHeader, in);
    }

    public static boolean isSetChunkSize(ChunkHeader chunkHeader) {
        /*These protocol control messages MUST have message stream ID 0 (known as the control stream) and be sent in chunk stream ID 2*/
        return chunkHeader.getBasicHeader().getCSId() == 2 && chunkHeader.getMessageHeader().getMSId() == 0;
    }
}

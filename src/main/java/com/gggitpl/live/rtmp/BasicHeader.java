package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import lombok.Builder;
import lombok.Data;

/**
 * @author gggitpl
 */
@Data
@Builder
public class BasicHeader {

    /**
     * 2 bit
     */
    private int chunkType;

    /**
     *
     */
    private int chunkStreamId;

    public static BasicHeader of(ByteBuf in) {
        final short b = in.readUnsignedByte();
        final BasicHeader basicHeader = BasicHeader.builder().chunkType(b >> 6).build();
        final int cSid = Binaries.six(b);
        if (cSid == 0) {
            /*Values 0 indicates the 2 byte form and an ID in the range of 64-319 (the second byte + 64)*/
            basicHeader.setChunkStreamId(in.readUnsignedByte() + 64);
        } else if (cSid == 1) {
            /*Values 1 indicates the 3 byte form and an ID in the range of 64-65599 ((the third byte)*256 + the second byte + 64)*/
            basicHeader.setChunkStreamId(in.readUnsignedByte() + 64 + in.readUnsignedByte() * 256);
        } else if (cSid > 1) {
            /*Values in the range of 3-63 represent the complete stream ID. Chunk Stream ID with value 2 is reserved for low-level protocol control messages and commands*/
            basicHeader.setChunkStreamId(cSid);
        } else {
            throw new RuntimeException("Unexpected EOF while reading RTMP packet basic header");
        }
        return basicHeader;
    }
}

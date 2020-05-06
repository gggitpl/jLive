package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import lombok.Builder;
import lombok.Data;

/**
 * @author gggitpl
 */
@Data
@Builder
public class MessageHeader {

    /**
     * 3 bytes
     */
    private int timestamp;

    /**
     * 3 bytes
     */
    private int timestampDelta;

    /**
     * 3 bytes
     */
    private int messageLength;

    /**
     * 1 bytes
     */
    private MessageTypeId messageTypeId;

    /**
     * 4 bytes
     */
    private long messageStreamId;

    public static MessageHeader of(ByteBuf in, BasicHeader basicHeader) {
        if (basicHeader.getChunkType() == 0) {
            return MessageHeader
                    .builder()
                    .timestamp(in.readUnsignedMedium())
                    .messageLength(in.readUnsignedMedium())
                    .messageTypeId(MessageTypeId.of(in.readUnsignedByte()))
                    .messageStreamId(in.readUnsignedInt())
                    .build();
        } else if (basicHeader.getChunkType() == 1) {
            return MessageHeader
                    .builder()
                    .timestampDelta(in.readUnsignedMedium())
                    .messageLength(in.readUnsignedMedium())
                    .messageTypeId(MessageTypeId.of(in.readUnsignedByte()))
                    .build();
        } else if (basicHeader.getChunkType() == 2) {
            return MessageHeader
                    .builder()
                    .timestampDelta(in.readUnsignedMedium())
                    .build();
        } else if (basicHeader.getChunkType() == 3) {
            return MessageHeader.builder().build();
        } else {
            throw new RuntimeException("Unexpected EOF while reading RTMP packet message header");
        }
    }


}

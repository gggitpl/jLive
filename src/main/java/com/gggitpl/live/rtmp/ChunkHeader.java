package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import lombok.Builder;
import lombok.Data;

@Data
public class ChunkHeader {

    private final BasicHeader basicHeader;

    private final MessageHeader messageHeader;

    /**
     * Extended Timestamp (0 or 4 bytes): This field is present in certain circumstances depending on the encoded timestamp or timestamp delta field in the Chunk Message header. See Section 5.3.1.3 for more information.
     */
    private final long extendedTimestamp;

    /**
     * Basic Header (1 to 3 bytes): This field encodes the chunk stream ID and the chunk type. Chunk type determines the format of the encoded message header. The length depends entirely on the chunk stream ID, which is a variable-length field.
     */
    @Data
    static class BasicHeader {
        /**
         * 2 bit
         */
        private final int cType;
        /**
         *
         */
        private final int cSId;

        public static BasicHeader of(ByteBuf in) {
            final short b = in.readUnsignedByte();
            final int cType = b >> 6;
            final int cSId = Binaries.six(b);
            if (cSId == 0) {
                /*Values 0 indicates the 2 byte form and an ID in the range of 64-319 (the second byte + 64)*/
                return new BasicHeader(cType, in.readUnsignedByte() + 64);
            } else if (cSId == 1) {
                /*Values 1 indicates the 3 byte form and an ID in the range of 64-65599 ((the third byte)*256 + the second byte + 64)*/
                return new BasicHeader(cType, in.readUnsignedByte() + 64 + in.readUnsignedByte() * 256);
            } else if (cSId < 0) {
                throw new RuntimeException("Unexpected EOF while reading RTMP packet basic header");
            }
            /*Values in the range of 3-63 represent the complete stream ID. Chunk Stream ID with value 2 is reserved for low-level protocol control messages and commands*/
            return new BasicHeader(cType, cSId);
        }
    }

    /**
     * Message Header (0, 3, 7, or 11 bytes): This field encodes information about the message being sent (whether in whole or in part). The length can be determined using the chunk type specified in the chunk header.
     */
    @Data
    @Builder
    static class MessageHeader {
        /**
         * 3 bytes
         */
        private int timestamp;

        /**
         * 3 bytes
         */
        private int delta;

        /**
         * 3 bytes
         */
        private int length;

        /**
         * 1 bytes
         */
        private MessageTypeId mTypeId;

        /**
         * 4 bytes
         */
        private long mSId;

        public static MessageHeader of(BasicHeader basicHeader, ByteBuf in) {
            if (basicHeader.getCType() == 0) {
                return MessageHeader
                        .builder()
                        .timestamp(in.readUnsignedMedium())
                        .length(in.readUnsignedMedium())
                        .mTypeId(MessageTypeId.of(in.readUnsignedByte()))
                        .mSId(in.readUnsignedInt())
                        .build();
            } else if (basicHeader.getCType() == 1) {
                return MessageHeader
                        .builder()
                        .delta(in.readUnsignedMedium())
                        .length(in.readUnsignedMedium())
                        .mTypeId(MessageTypeId.of(in.readUnsignedByte()))
                        .build();
            } else if (basicHeader.getCType() == 2) {
                return MessageHeader
                        .builder()
                        .delta(in.readUnsignedMedium())
                        .build();
            } else if (basicHeader.getCType() == 3) {
                return MessageHeader.builder().build();
            } else {
                throw new RuntimeException("Unexpected EOF while reading RTMP packet message header");
            }
        }
    }

    public static ChunkHeader of(ByteBuf in) {
        final BasicHeader basicHeader = BasicHeader.of(in);
        final MessageHeader messageHeader = MessageHeader.of(basicHeader, in);
        final long extendedTimestamp = (messageHeader.getTimestamp() >= 0xFFFFFF || messageHeader.getDelta() >= 0xFFFFFF) ? in.readUnsignedInt() : 0;
        return new ChunkHeader(basicHeader, messageHeader, extendedTimestamp);
    }
}

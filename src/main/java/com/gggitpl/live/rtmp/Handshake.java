package com.gggitpl.live.rtmp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;
import java.util.Random;

/**
 * @author gggitpl
 */
@Data
@Builder
public class Handshake {

    public static final short VERSION = 0x03;

    public static final int HANDSHAKE_SIZE = 1536;

    public static byte[] RANDOM_BYTES = new byte[1528];

    static {
        Random random = new Random(Instant.now().toEpochMilli());
        random.nextBytes(RANDOM_BYTES);
    }

    private C0C1 c0C1;
    private C2 c2;

    @Data
    @Builder
    static class C0C1 {
        private short version;

        private long time;

        private long zero;

        private byte[] randomData;
    }

    @Data
    @Builder
    static class C2 {
        private long time;

        private long time2;

        private byte[] randomEcho;
    }

    public static Handshake ofC0C1(ByteBuf in) {
        final byte[] bytes = new byte[1528];
        final C0C1 c0C1 = C0C1
                .builder()
                .version(in.readUnsignedByte())
                .time(in.readUnsignedInt())
                .zero(in.readUnsignedInt())
                .build();
        in.readBytes(bytes);
        c0C1.setRandomData(bytes);
        return Handshake.builder().c0C1(c0C1).build();
    }

    public static Handshake ofC2(ByteBuf in) {
        final byte[] bytes = new byte[1528];
        final C2 c2 = C2
                .builder()
                .time(in.readUnsignedInt())
                .time2(in.readUnsignedInt())
                .build();
        in.readBytes(bytes);
        c2.setRandomEcho(bytes);
        return Handshake.builder().c2(c2).build();
    }

    public static boolean isC0C1(ByteBuf in) {
        return in.readableBytes() == HANDSHAKE_SIZE + 1 && in.getUnsignedByte(0) == 0x03;
    }

    public static boolean isC2(ByteBuf in) {
        return in.readableBytes() == HANDSHAKE_SIZE;
    }

    public boolean isC0C1() {
        return Objects.nonNull(c0C1);
    }

    public boolean isC2() {
        return Objects.nonNull(c2);
    }

    public ByteBuf getS0S1(ChannelHandlerContext ctx) {
        return ctx.alloc().buffer()
                .writeByte(VERSION)
                .writeInt((int) Instant.now().toEpochMilli() / 1000)
                .writeInt(0)
                .writeBytes(RANDOM_BYTES);
    }

    public ByteBuf getS2(ChannelHandlerContext ctx) {
        final long timestamp = Instant.now().toEpochMilli() / 1000;
        return ctx.alloc().buffer()
                .writeInt((int) timestamp)
                .writeInt((int) timestamp)
                .writeBytes(RANDOM_BYTES);
    }
}

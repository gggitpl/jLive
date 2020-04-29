package com.gggitpl.live.rtmp.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.Serializable;


/**
 * @author gggitpl
 */
@Data
public class Handshake implements Serializable {

    private static final long serialVersionUID = 5875055815354785985L;

    public static final short VERSION = 3;

    /**
     * Version (8 bits)
     */
    private final short version;

    /**
     * Time (4 bytes)
     */
    private final long time;

    /**
     * Zero (4 bytes)
     */
    private final long zero;

    /**
     * Random data (1528 bytes)
     */
    private final ByteBuf data;

    public static Handshake of(ByteBuf content) {
        return new Handshake(content.readUnsignedByte(), content.readUnsignedInt(), content.readUnsignedInt(), Unpooled.wrappedBuffer(content));
    }

    public static boolean isC0(ByteBuf content) {
        return content.getUnsignedByte(0) == VERSION;
    }
}

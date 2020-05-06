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

    private int cType;

    private int csId;

    public static BasicHeader of(ByteBuf in) {
        final short basicHeaderByte = in.readUnsignedByte();
        final BasicHeader basicHeader = BasicHeader.builder().cType(basicHeaderByte >> 6).build();
        final int csId = Binaries.six(basicHeaderByte);
        if (csId == 0) {
            basicHeader.setCsId(in.readUnsignedByte() + 64);
        } else if (csId == 1) {
            basicHeader.setCsId(in.readUnsignedByte() + 64 + in.readUnsignedByte() * 256);
        } else if (csId >= 2 && csId <= 63) {
            basicHeader.setCsId(csId);
        }
        return basicHeader;
    }
}

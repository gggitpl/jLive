package com.gggitpl.live.rtmp.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Basic Header (1 to 3 bytes):  This field encodes the chunk stream IDand the chunk type.  Chunk type determines the format of theencoded message header.  The length depends entirely on the chunkstream ID, which is a variable-length field.
 *
 * @author gggitpl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasicHeader extends Message {
    private static final long serialVersionUID = 1369778513940635649L;

    private final long streamId;

    private final long type;
}

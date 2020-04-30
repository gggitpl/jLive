package com.gggitpl.live.rtmp.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gggitpl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Chunk extends Message{

    private static final long serialVersionUID = 7509402377223226099L;

    private final BasicHeader basicHeader;

    /**
     * Extended Timestamp (0 or 4 bytes):  This field is present in certaincircumstances depending on the encoded timestamp or timestampdelta field in the Chunk Message header.  See Section 5.3.1.3 formore information.
     */
    private final MessageHeader messageHeader;

    private final long extendedTimestamp;

    /**
     * Chunk Data (variable size):  The payload of this chunk, up to theconfigured maximum chunk size.
     */
    private final byte[] chunkData;

}

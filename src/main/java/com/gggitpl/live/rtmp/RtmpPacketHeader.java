package com.gggitpl.live.rtmp;

import lombok.Data;

/**
 * @author gggitpl
 */
@Data
public class RtmpPacketHeader {
    private Byte headType;
    private Integer timer;
    private Integer amfSize;
    private Byte amfType;
    private Integer streamId;
}

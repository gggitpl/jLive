package com.gggitpl.live.rtmp.message;

/**
 * Message Header (0, 3, 7, or 11 bytes):  This field encodesinformation about the message being sent (whether in whole or inpart).  The length can be determined using the chunk typespecified in the chunk header.
 *
 * @author gggitpl
 */
public class MessageHeader extends Message{
    private static final long serialVersionUID = 3256398918856434274L;
}

package com.gggitpl.live.rtmp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
public enum MessageTypeId {
    /*Protocol Control Message*/
    SET_CHUNK_SIZE((short) 1),
    ABORT_MESSAGE((short) 2),
    ACKNOWLEDGEMENT((short) 3),
    WINDOW_ACKNOWLEDGEMENT_SIZE((short) 5),
    SET_PEER_BANDWIDTH((short) 6),
    /*Command Message*/
    COMMAND_MESSAGE_AMF0((short) 20),
    COMMAND_MESSAGE_AMF3((short) 17),
    /*Data Message*/
    DATA_MESSAGE_AMF0((short) 18),
    DATA_MESSAGE_AMF3((short) 15),
    /*Shared Object Message*/
    SHARED_OBJECT_MESSAGE_AMF0((short) 19),
    SHARED_OBJECT_MESSAGE_AMF3((short) 16),
    AUDIO_MESSAGE((short) 8),
    VIDEO_MESSAGE((short) 9),
    AGGREGATE_MESSAGE((short) 22),
    USER_CONTROL_MESSAGE_EVENTS((short) 4);

    private final short value;

    private static final Map<Short, MessageTypeId> MAP = Arrays.stream(values()).collect(Collectors.toMap(MessageTypeId::getValue, o -> o));

    public static MessageTypeId of(Short value) {
        return MAP.get(value);
    }
}

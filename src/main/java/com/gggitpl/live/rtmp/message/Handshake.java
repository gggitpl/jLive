package com.gggitpl.live.rtmp.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Random;


/**
 * @author gggitpl
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Handshake extends Message {

    private static final long serialVersionUID = 9054762794580326800L;

    /**
     * packets are 1536 octets long
     */
    private static final int HANDSHAKE_LEN = 0x600;

    private static final short VERSION = 0x3;

    /**
     * Version (8 bits):  In C0, this field identifies the RTMP versionrequested by the client.  In S0, this field identifies the RTMPversion selected by the server.  The version defined by thisspecification is 3.  Values 0-2 are deprecated values used byearlier proprietary products; 4-31 are reserved for futureimplementations; and 32-255 are not allowed (to allowdistinguishing RTMP from text-based protocols, which always startwith a printable character).  A server that does not recognize theclient’s requested version SHOULD respond with 3.  The client MAYchoose to degrade to version 3, or to abandon the handshake.
     */
    private final short version;

    /**
     * Time (4 bytes):  This field contains a timestamp, which SHOULD beused as the epoch for all future chunks sent from this endpoint.This may be 0, or some arbitrary value.  To synchronize multiplechunkstreams, the endpoint may wish to send the current value ofthe other chunkstream’s timestamp.
     */
    private final long time;

    /**
     * Zero (4 bytes):  This field MUST be all 0s.
     */
    private final long zero;

    /**
     * Random data (1528 bytes):  This field can contain any arbitraryvalues.  Since each endpoint has to distinguish between theresponse to the handshake it has initiated and the handshakeinitiated by its peer,this data SHOULD send something sufficientlyrandom.  But there is no need for cryptographically-securerandomness, or even dynamic values.
     */
    private final byte[] randomData;

    /**
     * Time2 (4 bytes):  This field MUST contain the timestamp at which theprevious packet(s1 or c1) sent by the peer was read.
     */
    private final long time2;

    /**
     * Random echo (1528 bytes):  This field MUST contain the random datafield sent by the peer in S1 (for C2) or S2 (for C1).  Either peercan use the time and time2 fields together with the currenttimestamp as a quick estimate of the bandwidth and/or latency ofthe connection, but this is unlikely to be useful.
     */
    private final byte[] randomEcho;

    public boolean isC0C1() {
        return version == VERSION;
    }

    public static boolean isC0C1(ByteBuf content) {
        return content.getUnsignedByte(0) == VERSION && content.readableBytes() == HANDSHAKE_LEN + 1;
    }

    public static Handshake ofC0C1(ByteBuf content) {
        return Handshake.builder()
                .version(content.readUnsignedByte())
                .time(content.readUnsignedInt())
                .zero(content.readUnsignedInt())
                .randomData(ByteBufUtil.getBytes(content))
                .build();
    }

    public Handshake buildS0S1S2() {
        final long timestamp = Instant.now().toEpochMilli();
        Random random = new Random(timestamp);
        final byte[] bytes = new byte[1528];
        random.nextBytes(bytes);
        return Handshake.builder()
                .version(VERSION)
                .time(timestamp / 1000)
                .time2(timestamp / 1000)
                .zero(0)
                .randomData(bytes)
                .randomEcho(bytes)
                .build();
    }
}

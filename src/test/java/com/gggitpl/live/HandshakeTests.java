package com.gggitpl.live;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

@Slf4j
public class HandshakeTests {

    String handshake = "020000004500007503c2400040060000c0a80009c0a800090c9a078f62ffd8e74187a4d1501820140c9f000000e22800000c2f00009b540000b46600004767000065430000384e00002a6600004673000089120000a950000082330000792000007a110000766d000078080000c236000063490000b1260000";

    @Test
    void test() {
        final ByteBuf byteBuf = Unpooled.wrappedBuffer(handshake.getBytes());
        log.debug("{}", byteBuf.toString(StandardCharsets.UTF_8));
        final int i = byteBuf.readInt();
        System.out.println(i);
    }
}

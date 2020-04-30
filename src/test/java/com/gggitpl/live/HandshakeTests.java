package com.gggitpl.live;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@Slf4j
public class HandshakeTests {


    @Test
    void test() {
        System.out.println(Instant.now().toEpochMilli()/1000);
    }
}

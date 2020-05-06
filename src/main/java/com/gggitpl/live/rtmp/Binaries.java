package com.gggitpl.live.rtmp;

import static java.lang.Math.pow;

/**
 * @author gggitpl
 */
public abstract class Binaries {

    public static int one(final int val) {
        return (int) (val % pow(2, 1));
    }

    public static int two(final int val) {
        return (int) (val % pow(2, 2));
    }

    public static int three(final int val) {
        return (int) (val % pow(2, 3));
    }

    public static int four(final int val) {
        return (int) (val % pow(2, 4));
    }

    public static int five(final int val) {
        return (int) (val % pow(2, 5));
    }

    public static int six(final int val) {
        return (int) (val % pow(2, 6));
    }

    public static int seven(final int val) {
        return (int) (val % pow(2, 7));
    }

}

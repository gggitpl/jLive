package com.gggitpl.live.rtmp.message;

import java.io.*;

/**
 * @author gggitpl
 */
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 8425765928373075820L;

    public byte[] getBytes() {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}

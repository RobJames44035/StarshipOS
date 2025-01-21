/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.internal.net.http.websocket;

import java.io.*;
import java.nio.*;
import java.util.List;

/**
 * One of these supplied for each incoming client connection for use
 * by user written MessageStreamConsumer.
 */
interface MessageStreamResponder {

    public void sendText(CharBuffer src, boolean last) throws IOException;

    public void sendBinary(ByteBuffer src, boolean last) throws IOException;

    public void sendPing(ByteBuffer src) throws IOException;

    public void sendPong(ByteBuffer src) throws IOException;

    public void sendClose(int statusCode, CharBuffer reason) throws IOException;

    public void close();
}

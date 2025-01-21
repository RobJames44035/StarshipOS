/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.internal.net.http.websocket;

import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class WebSocketResponder implements MessageStreamResponder {

    final MessageStreamConsumer consumer;
    final LinkedList<ByteBuffer> queue;
    volatile boolean closed = false;

    final MessageEncoder encoder;
    final MessageDecoder decoder;

    static final int BUF_SIZE = 1024;

    public WebSocketResponder(MessageStreamConsumer consumer) {
        this.consumer = consumer;
        this.queue = new LinkedList<>();
        this.decoder = new MessageDecoder(consumer, true);
        this.encoder = new MessageEncoder(true);
    }

    // own thread
    public void readLoop(SocketChannel chan) throws IOException {
        chan.configureBlocking(true);
        boolean eof = false;
        ByteBuffer buf = ByteBuffer.allocate(8 * 1024);
        Frame.Reader reader = new Frame.Reader();
        try {
            while (!eof) {
                int count;
                buf.clear();
                eof = ((count=chan.read(buf)) == -1);
                if (!eof) {
                    buf.flip();
                    reader.readFrame(buf, decoder);
                }
            }
        } catch (IOException e) {
            if (!closed)
                throw e;
        }
    }

    // own thread
    public void writeLoop(SocketChannel chan) throws IOException {
        // read queue and send data
        while (true) {
            ByteBuffer buf;
            synchronized(queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new IOException(e);
                    }
                    if (queue.isEmpty() && closed) {
                        chan.close();
                        return;
                    }
                }
                buf = queue.remove(0);
            }
            chan.write(buf);
        }
    }

    /**
     * Public methods below used y MessageStreamHandler to send replies
     * to client.
     */
    @Override
    public void sendText(CharBuffer src, boolean last) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        LinkedList<ByteBuffer> bufs = new LinkedList<>();
        boolean done = false;
        do {
            buf.clear();
            done = encoder.encodeText(src, last, buf);
            buf.flip();
            bufs.add(buf);
        } while (!done);
        sendMessage(bufs);
    }

    @Override
    public void sendBinary(ByteBuffer src, boolean last) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        LinkedList<ByteBuffer> bufs = new LinkedList<>();
        boolean done = false;
        do {
            buf.clear();
            done = encoder.encodeBinary(src, last, buf);
            buf.flip();
            bufs.add(buf);
        } while (!done);
        sendMessage(bufs);
    }

    @Override
    public void sendPing(ByteBuffer src) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        LinkedList<ByteBuffer> bufs = new LinkedList<>();
        boolean done = false;
        do {
            buf.clear();
            done = encoder.encodePing(src, buf);
            buf.flip();
            bufs.add(buf);
        } while (!done);
        sendMessage(bufs);
    }

    @Override
    public void sendPong(ByteBuffer src) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        LinkedList<ByteBuffer> bufs = new LinkedList<>();
        boolean done = false;
        do {
            buf.clear();
            done = encoder.encodePong(src, buf);
            buf.flip();
            bufs.add(buf);
        } while (!done);
        sendMessage(bufs);
    }

    @Override
    public void sendClose(int statusCode, CharBuffer reason) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        LinkedList<ByteBuffer> bufs = new LinkedList<>();
        boolean done = false;
        do {
            buf.clear();
            done = encoder.encodeClose(statusCode, reason, buf);
            buf.flip();
            bufs.add(buf);
        } while (!done);
        sendMessage(bufs);
        close();
    }

    private void sendMessage(List<ByteBuffer> bufs) throws IOException {
        if (closed)
            throw new IOException("closed");
        synchronized(queue) {
            queue.addAll(bufs);
            queue.notify();
        }
    }

    @Override
    public void close() {
        synchronized(queue) {
            closed = true;
            queue.notify();
        }
    }
}

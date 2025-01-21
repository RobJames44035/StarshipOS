/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * Utility class for tests. A simple "in-thread" server to accept connections
 * and write bytes.
 * @author kladko
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.Closeable;

public class ByteServer implements Closeable {

    private final ServerSocket ss;
    private Socket s;

    ByteServer() throws IOException {
        this.ss = new ServerSocket(0);
    }

    SocketAddress address() {
        return new InetSocketAddress(ss.getInetAddress(), ss.getLocalPort());
    }

    void acceptConnection() throws IOException {
        if (s != null)
            throw new IllegalStateException("already connected");
        this.s = ss.accept();
    }

    void closeConnection() throws IOException {
        Socket s = this.s;
        if (s != null) {
            this.s = null;
            s.close();
        }
    }

    void write(int count) throws IOException {
        if (s == null)
            throw new IllegalStateException("no connection");
        s.getOutputStream().write(new byte[count]);
    }

    public void close() throws IOException {
        if (s != null)
            s.close();
        ss.close();
    }
}

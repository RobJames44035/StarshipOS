/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8245194
 * @library /test/lib
 * @run main/othervm NonBlockingAccept
 */

import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;

import jtreg.SkippedException;

public class NonBlockingAccept {

    static void checkSupported() {
        try {
            SocketChannel.open(StandardProtocolFamily.UNIX).close();
        } catch (UnsupportedOperationException e) {
            throw new SkippedException("Unix domain sockets not supported");
        } catch (Exception e) {
            // continue
        }
    }

    public static void main(String[] args) throws Exception {

        checkSupported();
        UnixDomainSocketAddress addr = null;

        try (ServerSocketChannel serverSocketChannel =
                                 ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            //non blocking mode
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(null);
            addr = (UnixDomainSocketAddress) serverSocketChannel.getLocalAddress();
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("The socketChannel is : expected Null " + socketChannel);
            if (socketChannel != null)
                throw new RuntimeException("expected null");
            // or exception could be thrown otherwise
        } finally {
            if (addr != null) {
                Files.deleteIfExists(addr.getPath());
            }
        }
    }
}


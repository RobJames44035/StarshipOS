/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @summary Test DatagramChannel's receive when port unreachable
 * @author Mike McCloskey
 */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class Refused {

    static ByteBuffer outBuf = ByteBuffer.allocateDirect(100);
    static ByteBuffer inBuf  = ByteBuffer.allocateDirect(100);
    static DatagramChannel client;
    static DatagramChannel server;
    static InetSocketAddress isa;

    public static void main(String[] args) throws Exception {
        outBuf.put("Blah Blah".getBytes());
        outBuf.flip();
        test1();

        // This test has been disabled because there are many circumstances
        // under which no ICMP port unreachable packets are received
        // See http://java.sun.com/j2se/1.4/networking-relnotes.html
        if ((args.length > 0) && (args[0].equals("test2"))) {
            outBuf.rewind();
            test2();
        }
    }

    public static void setup() throws Exception {
        client = DatagramChannel.open();
        server = DatagramChannel.open();

        client.socket().bind((SocketAddress)null);
        server.socket().bind((SocketAddress)null);

        client.configureBlocking(false);
        server.configureBlocking(false);

        InetAddress address = InetAddress.getLocalHost();
        int port = client.socket().getLocalPort();
        isa = new InetSocketAddress(address, port);
    }

    // Since this is not connected no PortUnreachableException should be thrown
    public static void test1() throws Exception {
        setup();

        server.send(outBuf, isa);
        server.receive(inBuf);

        client.close();

        outBuf.rewind();
        server.send(outBuf, isa);
        server.receive(inBuf);

        server.close();
    }

    // Test the connected case to see if PUE is thrown
    public static void test2() throws Exception {

        setup();
        server.configureBlocking(true);
        server.connect(isa);
        server.configureBlocking(false);
        outBuf.rewind();
        server.write(outBuf);
        server.receive(inBuf);

        client.close();
        Thread.sleep(2000);
        outBuf.rewind();

        try {
            server.write(outBuf);
            Thread.sleep(2000);
            inBuf.clear();
            server.read(inBuf);
        } catch (PortUnreachableException pue) {
            System.err.println("received PUE");
        }
        server.close();
    }
}

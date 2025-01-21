/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4132931
 * @summary DatagramSocket should use a factory for its impl
 *
 * @compile/module=java.base java/net/MyDatagramSocketImplFactory.java
 * @run main/othervm ADatagramSocket
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ADatagramSocket {
    public static void main(String[] args) throws Exception {
        // testing out setDatagramSocketImplFactory
        System.err.println("setting DatagramSocketImplFactory...");
        try {
          DatagramSocket.setDatagramSocketImplFactory(new java.net.MyDatagramSocketImplFactory());
        } catch (Exception ex) {
          throw new RuntimeException("Setting DatagramSocketImplFactory failed!");
        }

        QuoteServerThread server = new QuoteServerThread();
        int port = server.getPort();
        System.out.println("Server port is " + port);
        server.start();
        // Wait server thread to reach receive call
        server.readyToStart.await();

        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0);
        System.err.println("Success!! Server current time is: " + received);

        socket.close();
    }
}

class QuoteServerThread extends Thread {

    protected DatagramSocket socket = null;
    private final int port;
    final CountDownLatch readyToStart = new CountDownLatch(1);

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(0, InetAddress.getLocalHost());
        port =  socket.getLocalPort();
    }
    public int getPort(){
        return port;
    }

    public void run() {
      try {
        byte[] buf = new byte[256];

        // receive request
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // Notify client that server is ready to receive packet
        readyToStart.countDown();
        socket.receive(packet);

        // figure out response
        String dString = null;
        dString = new Date().toString();
        buf = dString.getBytes();

        // send the response to the client at "address" and "port"
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
      } catch (IOException e) {
        e.printStackTrace();
      }
      socket.close();
    }
}


/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @summary Sockets shouldn't be inherited when creating a child process
 */
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.net.*;
import java.io.*;

public class SocketInheritance {

    /*
     * Simple helper class to direct process output to the parent
     * System.out
     */
    static class IOHandler implements Runnable {
        InputStream in;

        IOHandler(InputStream in) {
            this.in = in;
        }

        static void handle(InputStream in) {
            IOHandler handler = new IOHandler(in);
            Thread thr = new Thread(handler);
            thr.setDaemon(true);
            thr.start();
        }

        public void run() {
            try {
                byte b[] = new byte[100];
                for (;;) {
                    int n = in.read(b);
                    if (n < 0) return;
                    System.out.write(b, 0, n);
                }
            } catch (IOException ioe) { }
        }

    }

    // connect to the given port
    static SocketChannel connect(int port) throws IOException {
        InetAddress lh = InetAddress.getLoopbackAddress();
        InetSocketAddress isa = new InetSocketAddress(lh, port);
        return SocketChannel.open(isa);
    }

    // simple child process that handshakes with the parent and then
    // waits indefinitely until it is destroyed
    static void child(int port) {
        try {
            connect(port).close();
        } catch (IOException x) {
            x.printStackTrace();
            return;
        }

        for (;;) {
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException x) { }
        }
    }


    // Creates a loopback connection.
    // Forks process which should not inherit the sockets.
    // Close the sockets, and attempt to re-bind the listener.

    static void start() throws Exception {

        // setup loopback connection
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind( new InetSocketAddress(0) );

        int port = ssc.socket().getLocalPort();

        SocketChannel sc1 = connect(port);
        SocketChannel sc2 = ssc.accept();

        // launch the child
        String cmd = System.getProperty("java.home") + File.separator + "bin" +
            File.separator + "java";
        String testClasses = System.getProperty("test.classes");
        if (testClasses != null)
            cmd += " -cp " + testClasses;
        cmd += " SocketInheritance -child " + port;

        Process p = Runtime.getRuntime().exec(cmd);

        IOHandler.handle(p.getInputStream());
        IOHandler.handle(p.getErrorStream());

        // wait for child to connect
        SocketChannel sc3 = ssc.accept();

        // close sockets
        sc1.close();
        sc2.close();
        sc3.close();
        ssc.close();

        // re-bind the listener - if the sockets were inherited then
        // this will fail
        try {
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.close();
        } finally {
            p.destroy();
        }

    }

    public static void main(String[] args) throws Exception {
        if (!System.getProperty("os.name").startsWith("Windows"))
            return;

        if (args.length == 0) {
            start();
        } else {
            if (args[0].equals("-child")) {
                child(Integer.parseInt(args[1]));
            }
        }
    }
}

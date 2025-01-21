/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4191147
 * @summary 1.2beta4 does not load user defined content handlers
 * @library /test/lib
 * @build UserContentHandler
 * @run main/othervm UserContentHandler
 */

/* Run in othervm mode since the test sets a system property, java.content.handler.pkgs,
 * that prepends a specific package prefix defining a text/plain content
 * handler. If other URLConnection tests run before this one they might trigger
 * the Sun implementation text/plain content handler in sun.net.www.content
 * to be loaded and cached, this will break this test.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import jdk.test.lib.net.URIBuilder;
import static java.net.Proxy.NO_PROXY;

public class UserContentHandler implements Runnable {

    ServerSocket ss;

    public void run() {
        try {

            Socket s = ss.accept();
            s.setTcpNoDelay(true);

            PrintStream out = new PrintStream(
                                 new BufferedOutputStream(
                                    s.getOutputStream() ));

            out.print("HTTP/1.1 200 OK\r\n");
            out.print("Content-Length: 11\r\n");
            out.print("Content-Type: text/plain\r\n");
            out.print("\r\n");
            out.print("l;ajfdjafd\n");
            out.flush();

            // don't close the connection immediately as otherwise
            // the http headers may not have been received and the
            // http client will re-connect.
            Thread.sleep(2000);

            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    UserContentHandler() throws Exception {

        InetAddress loopback = InetAddress.getLoopbackAddress();
        ss = new ServerSocket();
        ss.bind(new InetSocketAddress(loopback, 0));
        Thread thr = new Thread(this);
        thr.start();

        try {
            Object o = new COM.foo.content.text.plain();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Properties props = System.getProperties();
        props.put("java.content.handler.pkgs", "COM.foo.content");
        System.setProperties(props);

        URL u = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(ss.getLocalPort())
                .path("/anything.txt")
                .toURL();

        if (!(u.openConnection(NO_PROXY).getContent() instanceof String)) {
            throw new RuntimeException("Load user defined content handler failed.");
        } else {
            System.err.println("Load user defined content handler succeed!");
        }
    }

    public static void main(String args[]) throws Exception {
        new UserContentHandler();
    }
}

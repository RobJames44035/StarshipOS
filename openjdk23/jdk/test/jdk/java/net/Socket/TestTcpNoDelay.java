/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6404388
 * @library /test/lib
 * @summary VISTA: Socket setTcpNoDelay & setKeepAlive working incorrectly
 * @run main TestTcpNoDelay
 * @run main/othervm -Djava.net.preferIPv4Stack=true TestTcpNoDelay
 */

import java.net.*;
import java.io.IOException;
import jdk.test.lib.net.IPSupport;

public class TestTcpNoDelay
{
    public static void main(String[] args) {
        IPSupport.throwSkippedExceptionIfNonOperational();

        try {
            Socket socket = new Socket();
            boolean on = socket.getTcpNoDelay();
            System.out.println("Get TCP_NODELAY = " + on);

            boolean opposite = on ? false: true;
            System.out.println("Set TCP_NODELAY to " + opposite);
            socket.setTcpNoDelay(opposite);

            boolean noDelay = socket.getTcpNoDelay();
            System.out.println("Get TCP_NODELAY = " + noDelay);

            if (noDelay != opposite)
                throw new RuntimeException("setTcpNoDelay no working as expected");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

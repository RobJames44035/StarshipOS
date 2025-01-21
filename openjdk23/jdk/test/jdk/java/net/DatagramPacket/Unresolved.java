/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/**
 * @test
 *
 * @bug 5021519
 *
 * @summary java.lang.NullPointerException: null buffer || null address
 */
import java.net.*;

public class Unresolved {
    public static void main(String[] args) throws Exception {
        InetSocketAddress remAddr =  InetSocketAddress.createUnresolved( "foo.bar", 161  );
        try {
            DatagramPacket packet1 = new DatagramPacket(  "Hellooo!".getBytes(), "Hellooo!".length(), remAddr  );
        } catch (IllegalArgumentException e) {
            // OK! We do expect that
            return;
        }
        throw new RuntimeException("Setting an unresolved address in a DatagramPacket shouldn't be allowed");
    }
}

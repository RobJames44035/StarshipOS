/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @bug   6964714
 * @library /test/lib
 * @run main/othervm -Djava.net.preferIPv4Stack=true IPv4Only
 * @summary Test the networkinterface listing with java.net.preferIPv4Stack=true.
 */


import java.net.*;
import java.util.*;
import jdk.test.lib.net.IPSupport;

public class IPv4Only {
    public static void main(String[] args) throws Exception {
        if (IPSupport.hasIPv4()) {
            System.out.println("Testing IPv4");
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();
                Enumeration<InetAddress> addrs = nif.getInetAddresses();
                while (addrs.hasMoreElements()) {
                   InetAddress hostAddr = addrs.nextElement();
                   if ( hostAddr instanceof Inet6Address ){
                        throw new RuntimeException( "NetworkInterfaceV6List failed - found v6 address " + hostAddr.getHostAddress() );
                   }
                }
            }
        } else {
            try {
                NetworkInterface.getNetworkInterfaces();
                throw new RuntimeException("NetworkInterface.getNetworkInterfaces() should throw SocketException");
            } catch (SocketException expected) {
                System.out.println("caught expected exception: " + expected);
            }

            try {
                NetworkInterface.networkInterfaces();
                throw new RuntimeException("NetworkInterface.networkInterfaces() should throw SocketException");
            } catch (SocketException expected) {
                System.out.println("caught expected exception: " + expected);
            }
        }
    }
}

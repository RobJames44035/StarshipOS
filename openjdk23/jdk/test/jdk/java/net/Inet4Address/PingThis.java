/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/* @test
 * @bug 7163874 8133015
 * @library /test/lib
 * @summary InetAddress.isReachable is returning false
 *          for InetAdress 0.0.0.0 and ::0
 * @run main PingThis
 * @run main/othervm -Djava.net.preferIPv4Stack=true PingThis
 */

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.test.lib.net.IPSupport;

public class PingThis {
    public static void main(String args[]) throws Exception {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return;
        }
        IPSupport.throwSkippedExceptionIfNonOperational();

        List<String> addrs = new ArrayList<String>();

        if (IPSupport.hasIPv4()) {
            addrs.add("0.0.0.0");
        }
        if (IPSupport.hasIPv6()) {
            addrs.add("::0");
        }

        for (String addr : addrs) {
            InetAddress inetAddress = InetAddress.getByName(addr);
            System.out.println("The target ip is "
                    + inetAddress.getHostAddress());
            boolean isReachable = inetAddress.isReachable(3000);
            System.out.println("the target is reachable: " + isReachable);
            if (isReachable) {
                System.out.println("Test passed ");
            } else {
                System.out.println("Test failed ");
                throw new Exception("address " + inetAddress.getHostAddress()
                        + " can not be reachable!");
            }
        }
    }
}

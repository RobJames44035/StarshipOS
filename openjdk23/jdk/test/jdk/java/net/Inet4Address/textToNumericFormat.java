/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4749938 8087190
 * @summary Bug in the parsing IPv4 literal addresses
 * @run main/othervm -Djdk.net.hosts.file=HostFileDoesNotExist textToNumericFormat
*/

/**
 * We use a dummy name service which throws UHE any time it is called.
 * We do this because the "good" tests here should parse correctly
 * without needing to call the name service, and the bad tests will
 * not parse and then invoke the name service, where we expect
 * the exception.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class textToNumericFormat {

    public static void main(String[] args) throws UnknownHostException {
        List<String> goodList = new ArrayList<>();
        List<String> badList = new ArrayList<>();
        String goodAddrs[] = {
                           "224.0.1.0",
                           "238.255.255.255",
                           "239.255.255.255",
                           "239.255.65535",
                           "239.16777215",
                           "4294967295" };

        String badAddrs[] = {
                           "238.255.255.2550",
                           "256.255.255.255",
                           "238.255.2550.255",
                           "238.2550.255.255",
                           "2380.255.255.255",
                           "239.255.65536",
                           "239.16777216",
                           "4294967296",
                           ".1.1.1",
                           "1..1.1",
                           "1.1.1.",
                           "..." };

        for (int i=0; i<goodAddrs.length; i++) {
            try {
                // Value is an IP Address literal, Name Service will not be called
                InetAddress ia = InetAddress.getByName(goodAddrs[i]);
            } catch (UnknownHostException e) {
                // shouldn't have come here
                goodList.add(goodAddrs[i]);
            }

        }

        for (int i=0; i<badAddrs.length; i++) {
            try {
                // Value is not an IP Address literal, Name Service will be called
                InetAddress ia = InetAddress.getByName(badAddrs[i]);
                // shouldn't have come here
                badList.add(badAddrs[i]);
            } catch (UnknownHostException e) {
                // ignore
            }

        }

        // if either goodList or badList is not empty, throw exception
        if (goodList.size() > 0 || badList.size() > 0) {
            throw new RuntimeException((goodList.size() > 0?
                                        ("Good address not parsed: "+ goodList)
                                        : "") +
                                       (badList.size() > 0 ?
                                        ("Bad Address parsed: "+ badList)
                                        : ""));
        }

    }

}

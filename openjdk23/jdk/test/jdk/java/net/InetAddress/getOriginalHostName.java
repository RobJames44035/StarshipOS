/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8133196
 * @summary test functionality of getOriginalHostName(InetAddress)
 * @modules java.base/jdk.internal.access
 */

import java.net.InetAddress;

import jdk.internal.access.JavaNetInetAddressAccess;
import jdk.internal.access.SharedSecrets;

public class getOriginalHostName {

    private static final JavaNetInetAddressAccess jna =
        SharedSecrets.getJavaNetInetAddressAccess();

    public static void main(String[] args) throws Exception {
        final String HOST = "dummyserver.java.net";
        InetAddress ia = null;
        ia = InetAddress.getByName(HOST);
        testInetAddress(ia, HOST);
        ia = InetAddress.getByName("255.255.255.0");
        testInetAddress(ia, null);
        ia = InetAddress.getByAddress(new byte[]{1,1,1,1});
        testInetAddress(ia, null);
        ia = InetAddress.getLocalHost();
        testInetAddress(ia, ia.getHostName());
        ia = InetAddress.getLoopbackAddress();
        testInetAddress(ia, ia.getHostName());
    }


    private static void testInetAddress(InetAddress ia, String expected)
        throws Exception {

        System.out.println("Testing InetAddress: " + ia);
        System.out.println("Expecting original hostname of : " + expected);
        String origHostName = jna.getOriginalHostName(ia);
        System.out.println("via JavaNetAccess: " + origHostName);
        if (origHostName == null && expected != null) {
            throw new RuntimeException("Unexpected null. Testing:" + expected);
        } else if (expected != null && !origHostName.equals(expected)) {
            throw new RuntimeException("Unexpected hostname :" + origHostName);
        } else if (expected == null && origHostName != null) {
            throw new RuntimeException("Unexpected origHostName: " + origHostName);
        }
    }
}

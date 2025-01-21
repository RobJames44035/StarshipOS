/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8168840
 * @summary InetAddress.getByName() throws java.net.UnknownHostException no such
 * interface when used with virtual interfaces on Solaris
 */
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class SubNetworkInterfaceTest {

    public static void main(String args[]) throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            doReverseLookup(netIf);
        }
    }

    static void doReverseLookup(NetworkInterface netIf) throws SocketException, UnknownHostException {
        for (NetworkInterface subIf : Collections.list(netIf.getSubInterfaces())) {
            Enumeration<InetAddress> subInetAddresses = subIf.getInetAddresses();
            while (subInetAddresses != null && subInetAddresses.hasMoreElements()) {
                InetAddress inetAddress = subInetAddresses.nextElement();
                String reversalString = inetAddress.getHostAddress();
                //should not throw UHE in case of virtual sub interface
                InetAddress.getByName(reversalString);
            }
        }
    }
}

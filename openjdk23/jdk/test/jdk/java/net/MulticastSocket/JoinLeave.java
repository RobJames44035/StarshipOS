/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

import jdk.test.lib.NetworkConfiguration;
import jdk.test.lib.net.IPSupport;

/**
 * @test
 * @bug 4091811 4148753 4102731
 * @summary Test java.net.MulticastSocket joinGroup and leaveGroup
 * @library /test/lib
 * @build jdk.test.lib.NetworkConfiguration
 *        jdk.test.lib.Platform
 * @run main JoinLeave
 * @run main/othervm -Djava.net.preferIPv4Stack=true JoinLeave
 */
public class JoinLeave {

    public static void main(String args[]) throws IOException {
        IPSupport.throwSkippedExceptionIfNonOperational();
        InetAddress ip4Group = InetAddress.getByName("224.80.80.80");
        InetAddress ip6Group = InetAddress.getByName("ff02::a");

        NetworkConfiguration nc = NetworkConfiguration.probe();
        nc.ip4MulticastInterfaces().forEach(nic -> joinLeave(ip4Group, nic));
        nc.ip6MulticastInterfaces().forEach(nic -> joinLeave(ip6Group, nic));
    }

    static void joinLeave(InetAddress group, NetworkInterface nif) {
        System.out.println("Joining:" + group + " on " + nif);
        try (MulticastSocket soc = new MulticastSocket()) {
            soc.setNetworkInterface(nif);
            soc.joinGroup(group);
            soc.leaveGroup(group);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

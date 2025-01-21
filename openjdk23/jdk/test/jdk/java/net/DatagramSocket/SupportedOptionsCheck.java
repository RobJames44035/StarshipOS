/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8234148
 * @library /test/lib
 * @summary checks that the DatagramSocket supportedOptions set contains all
 *  MulticastSocket socket options
 * @run testng SupportedOptionsCheck
 */

import jdk.test.lib.Platform;
import org.testng.annotations.Test;

import java.net.DatagramSocket;
import java.net.StandardSocketOptions;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class SupportedOptionsCheck {

    @Test
    public void checkMulticastOptionsAreReturned() throws Exception {
        try (DatagramSocket ds = new DatagramSocket())
        {
            Set<?> options = ds.supportedOptions();
            Set<?> multicastOptions = Set.of(
                    StandardSocketOptions.IP_MULTICAST_IF,
                    StandardSocketOptions.IP_MULTICAST_TTL,
                    StandardSocketOptions.IP_MULTICAST_LOOP);

            if (!Platform.isWindows())
                assertTrue(options.containsAll(multicastOptions));
        }
    }
}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * A JVM with JDP on should send multicast JDP packets regularly.
 *
 * @author Alex Schenkman
 */

/*
 * @test JdpSpecificAddressTest
 * @summary Assert that we can read JDP packets from a multicast socket connection, on specific IP and port.
 *
 * @library /test/lib
 *
 * @build ClientConnection JdpTestUtil JdpTestCase JdpOnTestCase DynamicLauncher
 * @run main/othervm JdpSpecificAddressTest
 */


public class JdpSpecificAddressTest extends DynamicLauncher {

    final String testName = "JdpOnTestCase";

    public static void main(String[] args) throws Exception {
        DynamicLauncher launcher = new JdpSpecificAddressTest();
        launcher.run();
    }

    /**
     * Send Jdp multicast packets to the specified IP and port, 224.0.1.2:1234
     */
    protected String[] options() {
        String[] options = {
                "-Dcom.sun.management.jmxremote.authenticate=false",
                "-Dcom.sun.management.jmxremote.ssl=false",
                "-Dcom.sun.management.jmxremote=true",
                "-Dcom.sun.management.jmxremote.port=" + String.valueOf(jmxPort),
                "-Dcom.sun.management.jmxremote.autodiscovery=true",
                "-Dcom.sun.management.jdp.pause=1",
                "-Dcom.sun.management.jdp.name=" + jdpName,
                "-Dcom.sun.management.jdp.address=224.0.1.2",
                "-Dcom.sun.management.jdp.port=1234",
                "-Djava.util.logging.SimpleFormatter.format=%1$tF %1$tT %4$-7s %5$s %n",
                testName
        };
        return options;
    }

}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * A JVM with JDP on should send multicast JDP packets regularly.
 *
 * @author Alex Schenkman
 */

/*
 * @test JdpDefaultsTest
 * @summary Assert that we can read JDP packets from a multicast socket connection, on default IP and port.
 *
 * @library /test/lib
 *
 * @build ClientConnection JdpTestUtil JdpTestCase JdpOnTestCase DynamicLauncher
 * @run main/othervm JdpDefaultsTest
 */

public class JdpDefaultsTest extends DynamicLauncher {

    final String testName = "JdpOnTestCase";

    public static void main(String[] args) throws Exception {
        DynamicLauncher launcher = new JdpDefaultsTest();
        launcher.run();
    }

    /**
     * Send Jdp multicast packets to the default IP and port, 224.0.23.178:7095
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
                "-Djava.util.logging.SimpleFormatter.format=%1$tF %1$tT %4$-7s %5$s %n",
                testName
        };
        return options;
    }
}

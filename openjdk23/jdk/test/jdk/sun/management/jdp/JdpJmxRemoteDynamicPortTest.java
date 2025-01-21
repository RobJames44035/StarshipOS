/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test JdpJmxRemoteDynamicPortTest.java
 * @bug 8167337
 * @summary Verify a non-zero value is assigned to jmxremote.port
 *          when VM is started with jmxremote.port=0.
 *
 * @library /test/lib
 *
 * @build ClientConnection JdpTestUtil JdpTestCase JdpJmxRemoteDynamicPortTestCase DynamicLauncher
 * @run main/othervm JdpJmxRemoteDynamicPortTest
 */

import java.lang.management.ManagementFactory;

public class JdpJmxRemoteDynamicPortTest  extends DynamicLauncher {
    final String testName = "JdpJmxRemoteDynamicPortTestCase";

    public static void main(String[] args) throws Exception {
        DynamicLauncher launcher = new JdpJmxRemoteDynamicPortTest();
        launcher.run();
    }

    protected String[] options() {
        String[] options = {
                "-Dcom.sun.management.jmxremote.authenticate=false",
                "-Dcom.sun.management.jmxremote.ssl=false",
                "-Dcom.sun.management.jmxremote=true",
                "-Dcom.sun.management.jmxremote.port=0",
                "-Dcom.sun.management.jmxremote.autodiscovery=true",
                "-Dcom.sun.management.jdp.pause=1",
                "-Dcom.sun.management.jdp.name=" + jdpName,
                "-Djava.util.logging.SimpleFormatter.format=%1$tF %1$tT %4$-7s %5$s %n",
                testName
        };
        return options;
    }
}

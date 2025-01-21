/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5065264
 * @summary Tests that JNDI bind failure doesn't leave an orphan RMI
 * Connector Server object
 * @author Eamonn McManus
 *
 * @run clean JNDIFailureTest
 * @run build JNDIFailureTest
 * @run main JNDIFailureTest
 */

import java.io.IOException;
import javax.management.*;
import javax.management.remote.*;
import javax.management.remote.rmi.*;

public class JNDIFailureTest {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs = MBeanServerFactory.createMBeanServer();
        JMXServiceURL jndiUrl =
            new JMXServiceURL("service:jmx:rmi:///jndi/nonexistenthost/x");
        SpyServerImpl impl = new SpyServerImpl();
        JMXConnectorServer cs =
            new RMIConnectorServer(jndiUrl, null, impl, mbs);
        try {
            cs.start();
        } catch (IOException e) {
            e.printStackTrace();
            if (impl.exported) {
                System.out.println("TEST FAILS: server not unexported");
                System.exit(1);
            } else {
                if (cs.isActive()) {
                    System.out.println("TEST FAILS: server still active");
                    System.exit(1);
                }
                System.out.println("Test passed");
                return;
            }
        }
        System.out.println("TEST FAILS: start did not throw exception");
        System.exit(1);
    }

    private static class SpyServerImpl extends RMIJRMPServerImpl {
        SpyServerImpl() throws IOException {
            super(0, null, null, null);
        }

        protected void export() throws IOException {
            super.export();
            exported = true;
        }

        protected void closeServer() throws IOException {
            super.closeServer();
            exported = false;
        }

        boolean exported;
    }
}

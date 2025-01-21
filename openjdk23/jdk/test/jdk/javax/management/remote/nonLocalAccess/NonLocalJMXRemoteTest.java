/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;

/* @test
 * @bug 8174770
 * @summary Verify that JMX Registry rejects non-local access for bind, unbind, rebind.
 *    The test is manual because the (non-local) host and port running JMX must be supplied as properties.
 * @run main/othervm/manual -Djmx-registry.host=jmx-registry-host -Djmx-registry.port=jmx-registry-port NonLocalJMXRemoteTest
 */

/**
 * Verify that access checks for the Registry exported by JMX Registry.bind(),
 * .rebind(), and .unbind() are prevented on remote access to the registry.
 * The test verifies that the access check is performed *before* the object to be
 * bound or rebound is deserialized.
 * This tests the SingleEntryRegistry implemented by JMX.
 * This test is a manual test and uses JMX running on a *different* host.
 * JMX can be enabled in any Java runtime; for example:
 *
 * Note: Use remote host with latest JDK update release for invoking rmiregistry.
 *
 * Note: Test should be ran twice once using arg1 and once using arg2.
 *
 * login or ssh to the remote host and invoke rmiregistry with arg1.
 * It will not show any output.
 * Execute the test, after test completes execution, stop the server.
 *
 * repeat above step using arg2 and execute the test.
 *
 *
 * arg1: {@code $JDK_HOME/bin/rmiregistry \
 *         -J-Dcom.sun.management.jmxremote.port=8888 \
 *         -J-Dcom.sun.management.jmxremote.local.only=false \
 *         -J-Dcom.sun.management.jmxremote.ssl=false \
 *         -J-Dcom.sun.management.jmxremote.authenticate=false
 * }
 *
 *
 * replace "jmx-registry-host" with the hostname or IP address of the remote host
 * for property "-J-Dcom.sun.management.jmxremote.host" below.
 *
 * arg2: {@code $JDK_HOME/bin/rmiregistry \
 *         -J-Dcom.sun.management.jmxremote.port=8888 \
 *         -J-Dcom.sun.management.jmxremote.local.only=false \
 *         -J-Dcom.sun.management.jmxremote.ssl=false \
 *         -J-Dcom.sun.management.jmxremote.authenticate=false \
 *         -J-Dcom.sun.management.jmxremote.host="jmx-registry-host"
 * }
 *
 * On the first host modify the @run command above to replace "jmx-registry-host"
 * with the hostname or IP address of the different host and run the test with jtreg.
 */
public class NonLocalJMXRemoteTest {

    public static void main(String[] args) throws Exception {

        String host = System.getProperty("jmx-registry.host");
        if (host == null || host.isEmpty()) {
            throw new RuntimeException("Specify host with system property: -Djmx-registry.host=<host>");
        }
        int port = Integer.getInteger("jmx-registry.port", -1);
        if (port <= 0) {
            throw new RuntimeException("Specify port with system property: -Djmx-registry.port=<port>");
        }

        // Check if running the test on a local system; it only applies to remote
        String myHostName = InetAddress.getLocalHost().getHostName();
        Set<InetAddress> myAddrs = Set.of(InetAddress.getAllByName(myHostName));
        Set<InetAddress> hostAddrs = Set.of(InetAddress.getAllByName(host));
        if (hostAddrs.stream().anyMatch(i -> myAddrs.contains(i))
                || hostAddrs.stream().anyMatch(h -> h.isLoopbackAddress())) {
            throw new RuntimeException("Error: property 'jmx-registry.host' must not be the local host%n");
        }

        Registry registry = LocateRegistry.getRegistry(host, port);
        try {
            // Verify it is a JMX Registry
            registry.lookup("jmxrmi");
        } catch (NotBoundException nf) {
            throw new RuntimeException("Not a JMX registry, jmxrmi is not bound", nf);
        }

        try {
            registry.bind("foo", null);
            throw new RuntimeException("Remote access should not succeed for method: bind");
        } catch (Exception e) {
            assertIsAccessException(e);
        }

        try {
            registry.rebind("foo", null);
            throw new RuntimeException("Remote access should not succeed for method: rebind");
        } catch (Exception e) {
            assertIsAccessException(e);
        }

        try {
            registry.unbind("foo");
            throw new RuntimeException("Remote access should not succeed for method: unbind");
        } catch (Exception e) {
            assertIsAccessException(e);
        }
    }

    /**
     * Check the exception chain for the expected AccessException and message.
     * @param ex the exception from the remote invocation.
     */
    private static void assertIsAccessException(Throwable ex) {
        Throwable t = ex;
        while (!(t instanceof AccessException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof AccessException) {
            String msg = t.getMessage();
            int asIndex = msg.indexOf("Registry");
            int disallowIndex = msg.indexOf("disallowed");
            int nonLocalHostIndex = msg.indexOf("non-local host");
            if (asIndex < 0 ||
                    disallowIndex < 0 ||
                    nonLocalHostIndex < 0 ) {
                System.out.println("Exception message is " + msg);
                throw new RuntimeException("exception message is malformed", t);
            }
            System.out.printf("Found expected AccessException: %s%n%n", t);
        } else {
            throw new RuntimeException("AccessException did not occur when expected", ex);
        }
    }
}

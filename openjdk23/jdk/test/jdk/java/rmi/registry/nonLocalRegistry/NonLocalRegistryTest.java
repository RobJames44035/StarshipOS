/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;

/* @test
 * @bug 8174770
 * @summary Verify that Registry rejects non-local access for bind, unbind, rebind.
 *    The test is manual because the (non-local) host running rmiregistry must be supplied as a property.
 * @run main/othervm/manual -Dregistry.host=rmi-registry-host NonLocalRegistryTest
 */

/**
 * Verify that access checks for Registry.bind(), .rebind(), and .unbind()
 * are prevented on remote access to the registry.
 *
 * This test is a manual test and uses a standard rmiregistry running
 * on a *different* host.
 * The test verifies that the access check is performed *before* the object to be
 * bound or rebound is deserialized.
 *
 * Login or ssh to the different host and invoke {@code $JDK_HOME/bin/rmiregistry}.
 * It will not show any output.
 *
 * On the first host modify the @run command above to replace "rmi-registry-host"
 * with the hostname or IP address of the different host and run the test with jtreg.
 */
public class NonLocalRegistryTest {

    public static void main(String[] args) throws Exception {

        String host = System.getProperty("registry.host");
        if (host == null || host.isEmpty()) {
            throw new RuntimeException("Specify host with system property: -Dregistry.host=<host>");
        }

        // Check if running the test on a local system; it only applies to remote
        String myHostName = InetAddress.getLocalHost().getHostName();
        Set<InetAddress> myAddrs = Set.of(InetAddress.getAllByName(myHostName));
        Set<InetAddress> hostAddrs = Set.of(InetAddress.getAllByName(host));
        if (hostAddrs.stream().anyMatch(i -> myAddrs.contains(i))
                || hostAddrs.stream().anyMatch(h -> h.isLoopbackAddress())) {
            throw new RuntimeException("Error: property 'registry.host' must not be the local host%n");
        }

        Registry registry = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);

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
            int rrIndex = msg.indexOf("Registry.Registry");     // Obsolete error text
            int disallowIndex = msg.indexOf("disallowed");
            int nonLocalHostIndex = msg.indexOf("non-local host");
            if (asIndex < 0 ||
                    rrIndex != -1 ||
                    disallowIndex < 0 ||
                    nonLocalHostIndex < 0 ) {
                throw new RuntimeException("exception message is malformed", t);
            }
            System.out.printf("Found expected AccessException: %s%n%n", t);
        } else {
            throw new RuntimeException("AccessException did not occur when expected", ex);
        }
    }
}

/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4626311
 * @summary Protocol "rmi:" is used in contrary to spec.  The Naming.list
 * method should not append an "rmi:" scheme prefix to the URLs in the
 * array of Strings it returns.
 * @author Ann Wollrath
 *
 * @library ../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary
 * @run main/othervm RmiIsNoScheme
 */

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

public class RmiIsNoScheme implements Remote, Serializable {
    private RmiIsNoScheme() {}

    public static void main(String[] args) {

        System.err.println("\nRegression test for bug 4626311\n");

        try {
            Registry registry = TestLibrary.createRegistryOnEphemeralPort();
            int registryPort = TestLibrary.getRegistryPort(registry);
            Naming.rebind("//:" + registryPort + "/RmiIsNoScheme",
                          new RmiIsNoScheme());
            String name = Naming.list("//:" + registryPort)[0];
            System.err.println("name = " + name);
            if (name.startsWith("rmi:", 0) == false) {
                System.err.println("TEST PASSED: rmi scheme not present");
            } else {
                throw new RuntimeException("TEST FAILED: rmi scheme present!");
            }
        } catch (Exception e) {
            TestLibrary.bomb(e);
        }
    }
}

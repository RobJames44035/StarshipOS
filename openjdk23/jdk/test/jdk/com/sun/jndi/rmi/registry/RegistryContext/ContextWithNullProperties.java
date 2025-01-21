/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6676075
 * @summary RegistryContext (com.sun.jndi.url.rmi.rmiURLContext) coding problem
 * @modules jdk.naming.rmi/com.sun.jndi.rmi.registry java.rmi/sun.rmi.registry
 *     java.rmi/sun.rmi.server java.rmi/sun.rmi.transport java.rmi/sun.rmi.transport.tcp
 * @library ../../../../../../java/rmi/testlibrary
 * @build TestLibrary
 * @run main/othervm ContextWithNullProperties
 */

import com.sun.jndi.rmi.registry.RegistryContext;
import java.rmi.registry.Registry;

public class ContextWithNullProperties {
    public static void main(String[] args) throws Exception {
        Registry registry = TestLibrary.createRegistryOnEphemeralPort();
        int registryPort = TestLibrary.getRegistryPort(registry);
        System.out.println("Connecting to the default Registry...");
        // Connect to the default Registry.
        // Pass null as the JNDI environment properties (see final argument)
        RegistryContext ctx = new RegistryContext(null, registryPort, null);
    }
}

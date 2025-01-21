/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4387038
 * @summary Ensure that java.rmi.Naming.lookup functions properly for names
 *          containing embedded ':' characters.
 *
 * @library ../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary
 * @run main/othervm LookupNameWithColon
 */

import java.rmi.Naming;
import java.rmi.registry.Registry;

public class LookupNameWithColon {
    public static void main(String[] args) throws Exception {
        String[] names = {
            "fairly:simple", "somewhat:more/complicated",
            "multiple:colons:in:name"
        };

        Registry reg = TestLibrary.createRegistryOnEphemeralPort();
        int port = TestLibrary.getRegistryPort(reg);

        for (int i = 0; i < names.length; i++) {
            reg.rebind(names[i], reg);
            Naming.lookup("rmi://localhost:" + port + "/" + names[i]);
        }
    }
}

/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 */

import javax.management.remote.rmi.RMIJRMPServerImpl;
import javax.management.remote.rmi.RMIServer;

public class ImplVersionCommand {

    public static void main(String[] args) throws Exception {

        // Create RMIJRMPServerImpl
        //
        System.out.println("Create RMIJRMPServerImpl");
        RMIServer server = new RMIJRMPServerImpl(0, null, null, null);

        // Get the JMX Remote impl version from RMIServer
        //
        System.out.println("Get JMX Remote implementation version from RMIServer");
        String full_version = server.getVersion();
        System.out.println("RMIServer.getVersion() = "+ full_version);
        String impl_version = full_version.substring(
            full_version.indexOf("java_runtime_")+"java_runtime_".length());

        // Display JMX Remote impl version and Java Runtime version
        //
        System.out.println("JMX Remote implementation version   = " +
                           impl_version);
        System.out.println("Java Runtime implementation version = " +
                           args[0]);

        // Check JMX Remote impl version vs. Java Runtime  version
        //
        if (!impl_version.equals(args[0])) {
            // Test FAILED
            throw new IllegalArgumentException(
                "***FAILED: JMX Remote and Java Runtime versions do NOT match***");
        }
        // Test OK!
        System.out.println("JMX Remote and Java Runtime versions match.");
        System.out.println("Bye! Bye!");
    }
}

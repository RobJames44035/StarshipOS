/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 */

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class ImplVersionCommand {

    public static void main(String[] args) throws Exception {
        // Instantiate the MBean server
        //
        System.out.println("Create the MBean server");
        MBeanServer mbs = MBeanServerFactory.createMBeanServer();

        // Get the JMX implementation version from the MBeanServerDelegateMBean
        //
        System.out.println("Get the JMX implementation version");
        ObjectName mbsdName =
            new ObjectName("JMImplementation:type=MBeanServerDelegate");
        String mbsdAttribute = "ImplementationVersion";
        String mbsdVersion = (String) mbs.getAttribute(mbsdName, mbsdAttribute);

        // Display JMX implementation version and JVM implementation version
        //
        System.out.println("JMX implementation version          = " +
                           mbsdVersion);
        System.out.println("Java Runtime implementation version = " +
                           args[0]);

        // Check JMX implementation version vs. JVM implementation version
        //
        if (!mbsdVersion.equals(args[0]))
            throw new IllegalArgumentException(
              "JMX and Java Runtime implementation versions do not match!");
        // Test OK!
        //
        System.out.println("JMX and Java Runtime implementation " +
                           "versions match!");
        System.out.println("Bye! Bye!");
    }
}

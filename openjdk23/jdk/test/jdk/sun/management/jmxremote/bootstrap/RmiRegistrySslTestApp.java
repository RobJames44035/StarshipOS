/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import jdk.test.lib.Utils;


public class RmiRegistrySslTestApp {

    static final String ok = "OK: Found jmxrmi entry in RMIRegistry!";
    static final String ko = "KO: Did not find jmxrmi entry in RMIRegistry!";
    static final String ko2 = "KO: Did not get expected exception!";
    static final String okException = "OK: Got expected exception!";
    static final String koException = "KO: Got unexpected exception!";

    public static void main(String args[]) throws Exception {

        System.out.println("RmiRegistry lookup...");

        String testID = System.getProperty("testID");
        int port = Integer.parseInt(System.getProperty("test.rmi.port"));

        if ("Test1".equals(testID)) {
            try {
                Registry registry = LocateRegistry.getRegistry(port);
                String[] list = registry.list();
                if ("jmxrmi".equals(list[0])) {
                    System.out.println(ok);
                } else {
                    System.out.println(ko);
                    throw new IllegalArgumentException(ko);
                }
            } catch (Exception e) {
                System.out.println(koException);
                e.printStackTrace(System.out);
                throw e;
            }
        }

        if ("Test2".equals(testID)) {
            try {
                Registry registry = LocateRegistry.getRegistry(port);
                String[] list = registry.list();
                throw new IllegalArgumentException(ko2);
            } catch (Exception e) {
                System.out.println(okException);
                e.printStackTrace(System.out);
                return;
            }
        }

        if ("Test3".equals(testID)) {
            try {
                Registry registry = LocateRegistry.getRegistry(
                    null, port, new SslRMIClientSocketFactory());
                String[] list = registry.list();
                if ("jmxrmi".equals(list[0])) {
                    System.out.println(ok);
                } else {
                    System.out.println(ko);
                    throw new IllegalArgumentException(ko);
                }
            } catch (Exception e) {
                System.out.println(koException);
                e.printStackTrace(System.out);
                throw e;
            }
        }
    }
}

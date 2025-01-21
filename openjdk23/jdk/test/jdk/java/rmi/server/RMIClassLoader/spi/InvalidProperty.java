/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4418673
 * @summary verify that an inavlid setting of the system property
 * java.rmi.server.RMIClassLoaderSpi causes an Error to be thrown to users
 * of the RMIClassLoader API.
 * @author Peter Jones
 *
 * @library ../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary ServiceConfiguration
 * @run main/othervm InvalidProperty
 */

import java.rmi.server.RMIClassLoader;

public class InvalidProperty {

    public static void main(String[] args) throws Exception {

        ServiceConfiguration.installServiceConfigurationFile();

        System.setProperty(
            "java.rmi.server.RMIClassLoaderSpi", "NonexistentProvider");

        String classname = "Foo";

        try {
            System.err.println("first attempt:");
            Object ret;
            try {
                ret = RMIClassLoader.loadClass(classname);
            } catch (Exception e) {
                throw new RuntimeException(
                    "RMIClassLoader.loadClass threw exception", e);
            }
            throw new RuntimeException(
                "RMIClassLoader.loadClass returned " + ret);
        } catch (Error e) {
            System.err.println("RMIClassLoader.loadClass threw an Error:");
            e.printStackTrace();
        }

        try {
            System.err.println("second attempt:");
            Object ret;
            try {
                ret = RMIClassLoader.loadClass(classname);
            } catch (Exception e) {
                throw new RuntimeException(
                    "RMIClassLoader.loadClass threw exception", e);
            }
            throw new RuntimeException(
                "RMIClassLoader.loadClass returned " + ret);
        } catch (Error e) {
            System.err.println("RMIClassLoader.loadClass threw an Error:");
            e.printStackTrace();
        }

        System.err.println("TEST PASSED");
    }
}

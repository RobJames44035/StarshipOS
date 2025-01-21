/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4418673
 * @summary verify that the value of the java.rmi.server.RMIClassLoaderSpi
 * system property is interpreted to name the class to instantiate for the
 * RMIClassLoader provider.
 * @author Peter Jones
 *
 * @library ../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary ServiceConfiguration TestProvider
 * @run main/othervm Property
 */

public class Property {
    public static void main(String[] args) throws Exception {

        ServiceConfiguration.installServiceConfigurationFile();

        System.setProperty(
            "java.rmi.server.RMIClassLoaderSpi", "TestProvider");

        TestProvider.exerciseTestProvider(
            TestProvider.loadClassReturn,
            TestProvider.loadProxyClassReturn,
            TestProvider.getClassLoaderReturn,
            TestProvider.getClassAnnotationReturn,
            TestProvider.invocations);
    }
}

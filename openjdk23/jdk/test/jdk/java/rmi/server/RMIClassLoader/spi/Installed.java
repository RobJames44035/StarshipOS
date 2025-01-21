/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4418673
 * @summary verify that with the java.rmi.server.RMIClassLoaderSpi system
 * property not set, the first provider located through java.util.ServiceLoader
 * (which should be "TestProvider2") will be used by the RMIClassLoader API.
 * @author Peter Jones
 *
 * @library ../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary ServiceConfiguration TestProvider TestProvider2
 * @run main/othervm Installed
 */

public class Installed {
    public static void main(String[] args) throws Exception {

        ServiceConfiguration.installServiceConfigurationFile();

        TestProvider.exerciseTestProvider(
            TestProvider2.loadClassReturn,
            TestProvider2.loadProxyClassReturn,
            TestProvider2.getClassLoaderReturn,
            TestProvider2.getClassAnnotationReturn,
            TestProvider2.invocations);
    }
}

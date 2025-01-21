/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @bug 7102369 7094468 7100592
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @library ../../testlibrary
 * @build TestLibrary RMIRegistryRunner RegistryVM JavaVM testPkg.* RegistryLookup TestLoaderHandler
 * @summary remove java.rmi.server.codebase property parsing from registyimpl
 * @run main/othervm CodebaseTest
*/

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CodebaseTest {

    public static void main(String args[]) throws Exception {
        RegistryVM rmiregistry = null;
        JavaVM client = null;

        System.setProperty("java.rmi.server.RMIClassLoaderSpi", "TestLoaderHandler");

        try {
            File src = new File(System.getProperty("test.classes", "."), "testPkg");
            File dest = new File(System.getProperty("user.dir", "."), "testPkg");
            Files.move(src.toPath(), dest.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            File rmiregistryDir =
                new File(System.getProperty("user.dir", "."), "rmi_tmp");
            rmiregistryDir.mkdirs();
            rmiregistry = RegistryVM.createRegistryVMWithRunner(
                    "RMIRegistryRunner",
                    " -Djava.rmi.server.useCodebaseOnly=false"
                    + " -Duser.dir=" + rmiregistryDir.getAbsolutePath()
                    + " -Djava.rmi.server.RMIClassLoaderSpi=TestLoaderHandler");
            rmiregistry.start();
            int port = rmiregistry.getPort();

            File srcReadTest = new File(System.getProperty("test.classes", "."),
                                    "RegistryLookup.class");
            File destReadTest = new File(System.getProperty("user.dir", "."),
                                    "RegistryLookup.class");
            Files.move(srcReadTest.toPath(), destReadTest.toPath(),
                                    StandardCopyOption.REPLACE_EXISTING);

            File codebase = new File(System.getProperty("user.dir", "."));
            client = new JavaVM("RegistryLookup",
                    " -Djava.rmi.server.codebase=" + codebase.toURI().toURL()
                    + " -cp ." + File.pathSeparator + System.getProperty("test.class.path")
                    + " -Djava.rmi.server.RMIClassLoaderSpi=TestLoaderHandler",
                    Integer.toString(port));
            int exit = client.execute();
            if (exit == RegistryLookup.EXIT_FAIL) {
                throw new RuntimeException("Test Fails");
            }
        } finally {
            if (rmiregistry != null) {
                rmiregistry.cleanup();
            }
            if (client != null) {
                client.cleanup();
            }
        }
    }
}

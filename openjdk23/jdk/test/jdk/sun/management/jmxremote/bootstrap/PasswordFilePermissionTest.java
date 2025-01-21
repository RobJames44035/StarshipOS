/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.IOException;

/**
 * @test
 * @bug 6557093
 * @summary Check SSL config file permission for out-of-the-box management
 * @author Taras Ledkov
 *
 * @library /test/lib
 *
 * @build jdk.test.lib.Platform AbstractFilePermissionTest Dummy
 * @run main/timeout=300 PasswordFilePermissionTest
 */
public class PasswordFilePermissionTest extends AbstractFilePermissionTest {

    private PasswordFilePermissionTest() {
        super("jmxremote.passwordconfig");
    }

    public void testSetup() throws IOException {
        createFile(mgmt,
                "# management.properties",
                "com.sun.management.jmxremote.ssl=false",
                "com.sun.management.jmxremote.password.file=" + file2PermissionTest.toFile().getAbsolutePath());

        createFile(file2PermissionTest,
                "# jmxremote.password\n");
    }

    public static void main(String[] args) throws Exception {
        PasswordFilePermissionTest test = new PasswordFilePermissionTest();

        test.runTest(args);
    }

}

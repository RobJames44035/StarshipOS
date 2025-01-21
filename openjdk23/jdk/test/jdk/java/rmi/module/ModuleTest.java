/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib
 * @build jdk.test.lib.process.ProcessTools
 *        jdk.test.lib.compiler.CompilerUtils
 *        jdk.test.lib.util.JarUtils
 *        ModuleTest
 * @run testng ModuleTest
 * @summary Basic tests for using rmi in module world
 */

import static jdk.test.lib.process.ProcessTools.executeTestJava;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import jdk.test.lib.compiler.CompilerUtils;
import jdk.test.lib.util.JarUtils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ModuleTest {

    static String fileJoin(String... names) {
        return String.join(File.separator, names);
    }

    static String pathJoin(String... paths) {
        return String.join(File.pathSeparator, paths);
    }

    private static final String TEST_SRC = System.getProperty("test.src");
    private static final String CLIENT_EXP = fileJoin("exploded", "mclient");
    private static final String SERVER_EXP = fileJoin("exploded", "mserver");
    private static final String MTEST_EXP  = fileJoin("exploded", "mtest");
    private static final String CLIENT_JAR = fileJoin("mods", "mclient.jar");
    private static final String SERVER_JAR = fileJoin("mods", "mserver.jar");
    private static final String MTEST_JAR  = fileJoin("mods", "mtest.jar");

    private static final String DUMMY_MAIN = "testpkg.DummyApp";

    /**
     * Compiles all sample classes
     */
    @BeforeTest
    public void compileAll() throws Exception {
        assertTrue(CompilerUtils.compile(
                Paths.get(TEST_SRC, "src", "mserver"),
                Paths.get(SERVER_EXP)));

        JarUtils.createJarFile(
                Paths.get(SERVER_JAR),
                Paths.get(SERVER_EXP));

        assertTrue(CompilerUtils.compile(
                Paths.get(TEST_SRC, "src", "mclient"),
                Paths.get(CLIENT_EXP),
                "-cp", SERVER_JAR));

        JarUtils.createJarFile(
                Paths.get(CLIENT_JAR),
                Paths.get(CLIENT_EXP));

        assertTrue(CompilerUtils.compile(Paths.get(TEST_SRC, "src", "mtest"),
                Paths.get(MTEST_EXP),
                "-cp", pathJoin(CLIENT_JAR, SERVER_JAR)));

        JarUtils.createJarFile(
                Paths.get(MTEST_JAR),
                Paths.get(MTEST_EXP));
    }

    /**
     * Test the client, server and dummy application in different modules
     * @throws Exception
     */
    @Test
    public void testAllInModule() throws Exception {
        assertEquals(executeTestJava("--module-path", pathJoin(MTEST_JAR, CLIENT_JAR, SERVER_JAR),
                "--add-modules", "mclient,mserver",
                "-m", "mtest/" + DUMMY_MAIN)
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue(),
                0);
    }

    /**
     * Test the client and server in unnamed modules,
     * while the dummy application is in automatic module
     * @throws Exception
     */
    @Test
    public void testAppInModule() throws Exception {
        assertEquals(executeTestJava("--module-path", MTEST_JAR,
                "-cp", pathJoin(CLIENT_JAR, SERVER_JAR),
                "-m", "mtest/" + DUMMY_MAIN)
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue(),
                0);
    }

    /**
     * Test the client and server in automatic modules,
     * while the dummy application is in unnamed module
     * @throws Exception
     */
    @Test
    public void testAppInUnnamedModule() throws Exception {
        assertEquals(executeTestJava("--module-path", pathJoin(CLIENT_JAR, SERVER_JAR),
                "--add-modules", "mclient,mserver",
                "-cp", MTEST_JAR,
                DUMMY_MAIN)
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue(),
                0);
    }

    /**
     * Test the server and test application in automatic modules,
     * with client in unnamed module
     * @throws Exception
     */
    @Test
    public void testClientInUnnamedModule() throws Exception {
        assertEquals(executeTestJava("--module-path", pathJoin(MTEST_JAR, SERVER_JAR),
                "--add-modules", "mserver",
                "-cp", CLIENT_JAR,
                "-m", "mtest/" + DUMMY_MAIN)
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue(),
                0);
    }
}


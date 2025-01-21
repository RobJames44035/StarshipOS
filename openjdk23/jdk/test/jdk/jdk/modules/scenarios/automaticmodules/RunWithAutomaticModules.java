/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib
 * @modules jdk.compiler
 *          java.scripting
 *          jdk.zipfs
 * @build RunWithAutomaticModules
 *        jdk.test.lib.compiler.CompilerUtils
 *        jdk.test.lib.util.JarUtils
 *        jdk.test.lib.process.ProcessTools
 * @run testng RunWithAutomaticModules
 * @summary Runs tests that make use of automatic modules
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.compiler.CompilerUtils;
import jdk.test.lib.util.JarUtils;
import static jdk.test.lib.process.ProcessTools.*;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class RunWithAutomaticModules {

    private static final String TEST_SRC = System.getProperty("test.src");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path CLASSES_DIR = Paths.get("classes");
    private static final Path MODS_DIR = Paths.get("mods");

    /**
     * Basic test that consists of 3 modules:
     *
     * basictest - the test itself
     * httpserver - a JAR file (automatic module) with a dummy HTTP server
     * logging - a JAR file (automatic module) with a dummy logging library
     *
     * The test runs the dummy HTTP server and checks that has the expected
     * reads and exported packages.
     *
     * The HTTP server uses the logging library.
     */

    public void testBasic() throws Exception {
        boolean compiled;

        Path loggingSrc = SRC_DIR.resolve("logging");
        Path loggingClasses = CLASSES_DIR.resolve("logging");

        Path httpServerSrc = SRC_DIR.resolve("httpserver");
        Path httpServerClasses = CLASSES_DIR.resolve("httpserver");

        String testModule = "basictest";
        String mainClass = "test.Main";


        // compile + create mods/logging-1.0.jar

        compiled = CompilerUtils.compile(loggingSrc, loggingClasses);
        assertTrue(compiled);

        JarUtils.createJarFile(MODS_DIR.resolve("logging-1.0.jar"),
                               loggingClasses);


        // compile + create mods/httpserver-9.0.0.jar

        compiled = CompilerUtils.compile(httpServerSrc,
                httpServerClasses,
                "-cp", loggingClasses.toString());
        assertTrue(compiled);

        JarUtils.createJarFile(MODS_DIR.resolve("httpserver-9.0.0.jar"),
                httpServerClasses);


        // compile basictest to mods/basictest

        compiled = CompilerUtils
            .compile(SRC_DIR.resolve(testModule),
                    MODS_DIR.resolve(testModule),
                    "--module-path", MODS_DIR.toString());
        assertTrue(compiled);


        // launch the test. Need --add-mdoules because nothing explicitly depends on logging

        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "--add-modules", "logging",
                              "-m", testModule + "/" + mainClass)
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue();

        assertTrue(exitValue == 0);
    }



    /**
     * Test using a JAR file with a service provider as an automatic module.
     *
     * The consists of 2 modules:
     *
     * sptest - the test itself
     * bananascript - a JAR file (automatic module) with a dummy ScriptEngineFactory
     *
     * The test uses ServiceLoader to locate and load ScriptEngineFactory
     * implementations. It checks that bananascript is located.
     */

    public void testServiceProvider() throws Exception {
        boolean compiled;

        Path providerSrc = SRC_DIR.resolve("bananascript");
        Path providerClasses = CLASSES_DIR.resolve("bananascript");

        String testModule = "sptest";
        String mainClass = "test.Main";


        // create mods/bananascript-0.9.jar

        compiled = CompilerUtils.compile(providerSrc, providerClasses);
        assertTrue(compiled);

        String config = "META-INF/services/javax.script.ScriptEngineFactory";
        Path services = providerClasses.resolve(config).getParent();
        Files.createDirectories(services);
        Files.copy(providerSrc.resolve(config), providerClasses.resolve(config));

        JarUtils.createJarFile(MODS_DIR.resolve("bananascript-0.9.jar"), providerClasses);


        // compile sptest to mods/sptest

        compiled = CompilerUtils
                .compile(SRC_DIR.resolve(testModule),
                        MODS_DIR.resolve(testModule),
                        "--module-path", MODS_DIR.toString());

        assertTrue(compiled);


        // launch the test

        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "-m", testModule + "/" + mainClass)
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue();

        assertTrue(exitValue == 0);

    }

}

/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.io.File;

/*
 * @test
 * @modules jdk.compiler
 * @summary Test cases which run against the JDK image, check the situation where
 *            1. logger providers are in named module,
 *            2. clients are in named/unnamed module,
 *               patched system module, or Xbootclasspath
 *          This test does not require existence of java.logging module,
 *          but require jdk.compiler module
 * @requires vm.flagless
 * @library /test/lib
 * @build Base jdk.test.lib.compiler.CompilerUtils
 * @run main/othervm NamedLoggerForJDKTest
 */

public class NamedLoggerForJDKTest extends Base {

    public static void main(String args[]) throws Throwable {
        NamedLoggerForJDKTest t = new NamedLoggerForJDKTest();
        t.setup();
        t.test();
    }

    private void setup() throws Throwable {
        setupAllClient();

        setupNamedLogger();
    }

    private void test() throws Throwable {
        // logger client is in named module m.t.a
        runTest(JDK_IMAGE,
                "--module-path", DEST_NAMED_LOGGER.toString()
                    + File.pathSeparator + DEST_NAMED_CLIENT.toString(),
                "-m", CLIENT_A, "named", LOGGER_A);
        // logger client is in unnamed module
        runTest(JDK_IMAGE,
                "--module-path", DEST_NAMED_LOGGER.toString(),
                "--class-path", DEST_UNNAMED_CLIENT.toString(),
                CLIENT_B, "named", LOGGER_A);
        // logger client gets logger through boot class BootUsage
        runTest(JDK_IMAGE,
                "--module-path", DEST_NAMED_LOGGER.toString(),
                "-Xbootclasspath/a:" + DEST_BOOT_USAGE.toString(),
                "--class-path", DEST_BOOT_CLIENT.toString(),
                BOOT_CLIENT, "system", LAZY_LOGGER, LOGGER_A);
        // logger client gets logger through patched class
        // java.base/java.lang.PatchedUsage
        runTest(JDK_IMAGE,
                "--module-path", DEST_NAMED_LOGGER.toString(),
                "--patch-module", "java.base=" + DEST_PATCHED_USAGE.toString(),
                "--class-path", DEST_PATCHED_CLIENT.toString(),
                PATCHED_CLIENT, "system", LAZY_LOGGER, LOGGER_A);
    }
}

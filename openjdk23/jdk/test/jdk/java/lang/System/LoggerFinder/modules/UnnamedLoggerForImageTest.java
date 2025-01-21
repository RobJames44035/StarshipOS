/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.io.File;

/*
 * @test
 * @modules jdk.compiler
 * @summary Test cases which run against customized image, check the situation where
 *            1. logger providers are in unnamed module,
 *            2. clients are in named/unnamed module, image,
 *               patched system module, or Xbootclasspath
 *          This test does not require existence of java.logging module,
 *          but require jdk.compiler module
 * @requires vm.flagless
 * @library /test/lib
 * @build Base jdk.test.lib.compiler.CompilerUtils
 * @run main/othervm UnnamedLoggerForImageTest
 */

public class UnnamedLoggerForImageTest extends Base {

    public static void main(String args[]) throws Throwable {
        UnnamedLoggerForImageTest t = new UnnamedLoggerForImageTest();
        t.setup();
        t.test();
    }

    private void setup() throws Throwable {
        setupAllClient();

        setupUnnamedLogger();

        setupJavaBaseImage();
        setupClientImage();
    }

    private void test() throws Throwable {
        if (!checkJMODS()) {
            return;
        }

        // logger client is in unnamed module
        runTest(IMAGE,
                "--class-path", DEST_UNNAMED_LOGGER.toString()
                    + File.pathSeparator + DEST_UNNAMED_CLIENT.toString(),
                CLIENT_B, "unnamed", LOGGER_B);
        // logger client is in named module m.t.a
        runTest(IMAGE,
                "--class-path", DEST_UNNAMED_LOGGER.toString(),
                "--module-path", DEST_NAMED_CLIENT.toString(),
                "-m", CLIENT_A, "unnamed", LOGGER_B);
        // logger client is in named module m.t.a which is in customized image
        runTest(IMAGE_CLIENT,
                "--class-path", DEST_UNNAMED_LOGGER.toString(),
                "-m", CLIENT_A, "unnamed", LOGGER_B);
        // logger client gets logger through boot class BootUsage
        runTest(IMAGE,
                "--class-path", DEST_UNNAMED_LOGGER.toString()
                    + File.pathSeparator + DEST_BOOT_CLIENT.toString(),
                "-Xbootclasspath/a:" + DEST_BOOT_USAGE.toString(),
                BOOT_CLIENT, "system", LAZY_LOGGER, LOGGER_B);
        // logger client gets logger through patched class
        // java.base/java.lang.PatchedUsage
        runTest(IMAGE,
                "--class-path", DEST_UNNAMED_LOGGER.toString()
                    + File.pathSeparator + DEST_PATCHED_CLIENT.toString(),
                "--patch-module", "java.base=" + DEST_PATCHED_USAGE.toString(),
                PATCHED_CLIENT, "system", LAZY_LOGGER, LOGGER_B);
    }
}

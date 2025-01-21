/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @modules java.logging
 * @modules jdk.compiler
 * @summary Test cases which run against the JDK image, check the situation where
 *            1. logger provider is the default one supplied by the JDK,
 *            2. clients are in named/unnamed module,
 *               patched system module, or Xbootclasspath
 *          This test DOES require existence of java.logging module
 * @requires vm.flagless
 * @library /test/lib
 * @build Base jdk.test.lib.compiler.CompilerUtils
 * @run main/othervm JDKLoggerForJDKTest
 */

public class JDKLoggerForJDKTest extends Base {

    public static void main(String args[]) throws Throwable {
        JDKLoggerForJDKTest t = new JDKLoggerForJDKTest();
        t.setup();
        t.test();
    }

    private void setup() throws Throwable {
        setupAllClient();
    }

    private void test() throws Throwable {
        // logger client is in named module m.t.a
        runTest(JDK_IMAGE,
                "--module-path", DEST_NAMED_CLIENT.toString(),
                "-m", CLIENT_A, "system", JUL_LOGGER);
        // logger client is in unnamed module
        runTest(JDK_IMAGE,
                "--class-path", DEST_UNNAMED_CLIENT.toString(),
                CLIENT_B, "system", JUL_LOGGER);
        // logger client gets logger through boot class BootUsage
        runTest(JDK_IMAGE,
                "-Xbootclasspath/a:" + DEST_BOOT_USAGE.toString(),
                "--class-path", DEST_BOOT_CLIENT.toString(),
                BOOT_CLIENT, "system", LAZY_LOGGER, JUL_LOGGER);
        // logger client gets logger through patched class
        // java.base/java.lang.PatchedUsage
        runTest(JDK_IMAGE,
                "--patch-module", "java.base=" + DEST_PATCHED_USAGE.toString(),
                "--class-path", DEST_PATCHED_CLIENT.toString(),
                PATCHED_CLIENT, "system", LAZY_LOGGER, JUL_LOGGER);
    }
}

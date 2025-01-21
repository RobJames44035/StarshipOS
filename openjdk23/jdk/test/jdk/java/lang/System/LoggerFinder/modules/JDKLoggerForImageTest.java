/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @modules jdk.compiler
 * @summary Test cases which run against customized image, check the situation where
 *            1. logger provider is the default one supplied by java.base,
 *            2. clients are in named/unnamed module,
 *               patched system module, or Xbootclasspath
 *          This test does not require existence of java.logging module,
 *          but require jdk.compiler module
 * @requires vm.flagless
 * @library /test/lib
 * @build Base jdk.test.lib.compiler.CompilerUtils
 * @run main/othervm JDKLoggerForImageTest
 */

public class JDKLoggerForImageTest extends Base {

    public static void main(String args[]) throws Throwable {
        JDKLoggerForImageTest t = new JDKLoggerForImageTest();
        t.setup();
        t.test();
    }

    private void setup() throws Throwable {
        setupAllClient();

        setupJavaBaseImage();
    }

    private void test() throws Throwable {
        if (!checkJMODS()) {
            return;
        }

        // logger client is in named module m.t.a
        runTest(IMAGE,
                "--module-path", DEST_NAMED_CLIENT.toString(),
                "-m", CLIENT_A, "system", SIMPLE_LOGGER);
        // logger client is in unnamed module
        runTest(IMAGE,
                "--class-path", DEST_UNNAMED_CLIENT.toString(),
                CLIENT_B, "system", SIMPLE_LOGGER);
        // logger client gets logger through boot class BootUsage
        runTest(IMAGE,
                "-Xbootclasspath/a:" + DEST_BOOT_USAGE.toString(),
                "--class-path", DEST_BOOT_CLIENT.toString(),
                BOOT_CLIENT, "system", SIMPLE_LOGGER);
        // logger client gets logger through patched class
        // java.base/java.lang.PatchedUsage
        runTest(IMAGE,
                "--patch-module", "java.base=" + DEST_PATCHED_USAGE.toString(),
                "--class-path", DEST_PATCHED_CLIENT.toString(),
                PATCHED_CLIENT, "system", SIMPLE_LOGGER);
    }
}

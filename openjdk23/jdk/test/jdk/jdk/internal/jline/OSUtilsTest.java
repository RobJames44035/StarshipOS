/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8304498
 * @summary Verify the OSUtils class is initialized properly
 * @modules jdk.internal.le/jdk.internal.org.jline.utils
 */

import jdk.internal.org.jline.utils.OSUtils;

public class OSUtilsTest {
    public static void main(String... args) throws Exception {
        new OSUtilsTest().run();
    }

    void run() throws Exception {
        runTestTest();
    }

    void runTestTest() throws Exception {
        if (OSUtils.IS_WINDOWS) {
            return ; //skip on Windows
        }

        Process p = new ProcessBuilder(OSUtils.TEST_COMMAND, "-z", "").inheritIO().start();
        if (p.waitFor() != 0) {
            throw new AssertionError("Unexpected result!");
        }
    }
}

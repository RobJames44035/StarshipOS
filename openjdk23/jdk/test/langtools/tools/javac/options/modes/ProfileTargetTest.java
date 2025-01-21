/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8044859
 * @summary test support for checking -profile and -target
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build OptionModesTester
 * @run main ProfileTargetTest
 */

import com.sun.tools.javac.main.Main;
import java.io.IOException;

public class ProfileTargetTest extends OptionModesTester {
    public static void main(String... args) throws Exception {
        ProfileTargetTest t = new ProfileTargetTest();
        t.runTests();
    }

    @Test
    void testProfileTarget() throws IOException {
        writeFile("C.java", "class C { }");

        String[] opts = { "-profile", "compact1", "-source", "7", "-target", "7" };
        String[] files = { "C.java" };

        runMain(opts, files)
                .checkResult(Main.Result.CMDERR.exitCode);

        runCall(opts, files)
                .checkIllegalStateException();

        runParse(opts, files)
                .checkIllegalStateException();
    }
}

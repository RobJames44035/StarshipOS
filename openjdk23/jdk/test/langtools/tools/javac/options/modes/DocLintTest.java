/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8044859
 * @summary test support for -Xdoclint
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build OptionModesTester
 * @run main DocLintTest
 */

import com.sun.tools.javac.main.Main;
import java.io.IOException;

public class DocLintTest extends OptionModesTester {
    public static void main(String... args) throws Exception {
        DocLintTest t = new DocLintTest();
        t.runTests();
    }

    @Test
    void testDocLint() throws IOException {
        writeFile("src/C.java", "/** & */ class C { }");
        mkdirs("classes");

        String[] opts = { "-d", "classes", "-Xdoclint" };
        String[] files = { "src/C.java" };

        runMain(opts, files)
                .checkResult(Main.Result.ERROR.exitCode);

        runCall(opts, files)
                .checkResult(false);

        // 1. doclint runs at the beginning of analyze
        // 2. the analyze call itself succeeds, so we check for errors being reported
        runAnalyze(opts, files)
                .checkResult(true)
                .checkLog(Log.DIRECT, "^");
    }
}


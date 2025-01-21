/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8193107
 * @summary test an empty module
 * @modules jdk.javadoc/jdk.javadoc.internal.api
 *          jdk.javadoc/jdk.javadoc.internal.tool
 *          jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library ../../lib /tools/lib
 * @build toolbox.ToolBox toolbox.ModuleBuilder javadoc.tester.*
 * @run main TestEmptyModule
 */

import java.nio.file.Path;
import java.nio.file.Paths;

import javadoc.tester.JavadocTester;
import toolbox.ModuleBuilder;
import toolbox.ToolBox;

public class TestEmptyModule extends JavadocTester {

    public final ToolBox tb;
    public static void main(String... args) throws Exception {
        var tester = new TestEmptyModule();
        tester.runTests();
    }

    public TestEmptyModule() {
        tb = new ToolBox();
    }

    @Test
    public void checkEmptyModule(Path base) throws Exception {
        ModuleBuilder mb = new ModuleBuilder(tb, "empty")
                .comment("module empty.");
                mb.write(base);

        javadoc("-d", base.resolve("out").toString(),
                "-quiet",
                "--module-source-path", base.toString(),
                "--module", "empty");
        checkExit(Exit.OK);

        checkOutput("empty/module-summary.html", true,
                "module empty.");
    }

}

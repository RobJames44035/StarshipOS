/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8012447
 * @library /test/lib /testlibrary/ctw/src
 * @modules java.base/jdk.internal.access
 *          java.base/jdk.internal.jimage
 *          java.base/jdk.internal.misc
 *          java.base/jdk.internal.reflect
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @build jdk.test.whitebox.WhiteBox Foo Bar
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox Foo Bar
 * @run driver JarsTest prepare
 * @run driver JarsTest compile foo.jar bar.jar
 * @run driver JarsTest check
 * @summary testing of CompileTheWorld :: jars
 * @author igor.ignatyev@oracle.com
 */

import jdk.test.lib.process.OutputAnalyzer;

public class JarsTest extends CtwTest {
    private static final String[] SHOULD_CONTAIN
            = {"# jar: foo.jar", "# jar: bar.jar",
                    "Done (4 classes, 12 methods, "};

    private JarsTest() {
        super(SHOULD_CONTAIN);
    }

    public static void main(String[] args) throws Exception {
        new JarsTest().run(args);
    }

    protected void prepare() throws Exception {
        ProcessBuilder pb = createJarProcessBuilder("cf", "foo.jar",
                "Foo.class", "Bar.class");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        dump(output, "ctw-foo.jar");
        output.shouldHaveExitValue(0);

        pb = createJarProcessBuilder("cf", "bar.jar", "Foo.class", "Bar.class");
        output = new OutputAnalyzer(pb.start());
        dump(output, "ctw-bar.jar");
        output.shouldHaveExitValue(0);
    }

}

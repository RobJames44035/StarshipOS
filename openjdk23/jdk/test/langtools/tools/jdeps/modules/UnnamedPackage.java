/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8166846
 * @summary Tests --generate-module-info on invalid JAR file
 * @modules jdk.jdeps/com.sun.tools.jdeps
 * @library ../lib
 * @build JdepsRunner JdepsUtil
 * @run main UnnamedPackage
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class UnnamedPackage {
    private static final Path TEST_CLASSES = Paths.get(System.getProperty("test.classes"));
    private static final Path FOO_JAR_FILE = Paths.get("foo.jar");

    public static void main(String... args) throws Exception {
        // create foo.jar with unnamed package
        Path name = TEST_CLASSES.resolve("UnnamedPackage.class");
        JdepsUtil.createJar(FOO_JAR_FILE, TEST_CLASSES, Stream.of(name));

        // run jdeps --generate-module-info
        JdepsRunner jdeps = new JdepsRunner("--generate-module-info",
                                            "tmp", FOO_JAR_FILE.toString());
        // should fail to generate module-info.java
        int exitValue = jdeps.run();
        if (exitValue == 0) {
            throw new RuntimeException("expected non-zero exitValue");
        }
        if (!jdeps.outputContains("foo.jar contains an unnamed package")) {
            jdeps.printStdout(System.out);
            throw new RuntimeException("expected error message not found");
        }
    }
}

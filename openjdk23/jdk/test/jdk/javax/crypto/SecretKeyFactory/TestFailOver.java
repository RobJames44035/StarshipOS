/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import jdk.test.lib.compiler.CompilerUtils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @bug 6370923 8180568
 * @summary SecretKeyFactory failover does not work
 * @library /test/lib
 * @build jdk.test.lib.compiler.CompilerUtils
 * @run main TestFailOver
 */
public class TestFailOver {

    private static final Path SRC = Paths.get(System.getProperty("test.src"));
    private static final String P1_JAR
            = SRC.resolve("P1.jar").toFile().getAbsolutePath();
    private static final String P2_JAR
            = SRC.resolve("P2.jar").toFile().getAbsolutePath();
    private static final String SEC_PROP
            = SRC.resolve("security.properties").toFile().getAbsolutePath();
    private static final String JF_NAME = "FailOverTest";
    private static final Path SRC_PATH = SRC.resolve(JF_NAME + ".java");
    private static final Path COMPILE_PATH = Paths.get(".");
    private static final String PS = File.pathSeparator;

    public static void main(String[] args) throws Exception {

        List<String> params = getParameters();
        // Compile all source files.
        boolean done = CompilerUtils.compile(SRC_PATH, COMPILE_PATH,
                params.toArray(String[]::new));
        if (!done) {
            throw new RuntimeException("Test setup failed.");
        }
        params.add(0, "-Djava.security.properties=" + SEC_PROP);
        params.add(JF_NAME);
        OutputAnalyzer oa = ProcessTools.executeTestJava(
                params.toArray(String[]::new));
        System.out.println(oa.getOutput());
        oa.shouldHaveExitValue(0);
    }

    private static List<String> getParameters() {

        List<String> cmds = new ArrayList<>();
        cmds.add("-cp");
        cmds.add(P1_JAR + PS + P2_JAR + PS + COMPILE_PATH);
        return cmds;
    }

}

/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */



/*
 * @test JsrRewriting
 * @summary JSR (jump local subroutine)
 *      rewriting can overflow memory address size variables
 * @bug 7020373
 * @bug 7055247
 * @bug 7053586
 * @bug 7185550
 * @bug 7149464
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.desktop
 *          java.management
 * @run driver JsrRewriting
 */

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.io.File;

public class JsrRewriting {

    public static void main(String[] args) throws Exception {

        // ======= Configure the test
        String jarFile = System.getProperty("test.src") +
            File.separator + "JsrRewritingTestCase.jar";
        String className = "OOMCrashClass4000_1";

        // limit is 768MB in native words
        int mallocMaxTestWords = (1024 * 1024 * 768 / 4);
        if (Platform.is64bit())
            mallocMaxTestWords = (mallocMaxTestWords / 2);

        // ======= extract the test class
        ProcessBuilder pb = new ProcessBuilder(new String[] {
            JDKToolFinder.getJDKTool("jar"),
            "xvf", jarFile } );
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);

        // ======= execute the test
        // We run the test with MallocLimit set to 768m in oom mode,
        // in order to trigger and observe a fake os::malloc oom. This needs NMT.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-cp", ".",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:NativeMemoryTracking=summary",
            "-XX:MallocLimit=768m:oom",
            className);

        output = new OutputAnalyzer(pb.start());
        output.shouldNotHaveExitValue(0);
        String[] expectedMsgs = {
            "java.lang.LinkageError",
            "java.lang.NoSuchMethodError",
            "Main method not found in class " + className,
            "insufficient memory"
        };

        MultipleOrMatch(output, expectedMsgs);
    }

    private static void
        MultipleOrMatch(OutputAnalyzer analyzer, String[] whatToMatch) {
            String output = analyzer.getOutput();

            for (String expected : whatToMatch)
                if (output.contains(expected))
                    return;

            String err =
                " stdout: [" + analyzer.getOutput() + "];\n" +
                " exitValue = " + analyzer.getExitValue() + "\n";
            System.err.println(err);

            StringBuilder msg = new StringBuilder("Output did not contain " +
                "any of the following expected messages: \n");
            for (String expected : whatToMatch)
                msg.append(expected).append(System.lineSeparator());
            throw new RuntimeException(msg.toString());
    }
}


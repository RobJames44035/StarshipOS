/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Test verifies that -XX:HeapDumpGzipLevel=0 works
 * @library /test/lib
 * @run driver/timeout=240 TestGZippedHeapDumpOnOutOfMemoryError run 0
 */

/*
 * @test
 * @summary Test verifies that -XX:HeapDumpGzipLevel=1 works
 * @library /test/lib
 * @requires vm.flagless
 * @run driver/timeout=240 TestGZippedHeapDumpOnOutOfMemoryError run 1
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.hprof.HprofParser;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.File;

public class TestGZippedHeapDumpOnOutOfMemoryError {

    static volatile Object[] oa;

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            test(Integer.parseInt(args[1]));
            return;
        }

        try {
            oa = new Object[Integer.MAX_VALUE];
            throw new Error("OOME not triggered");
        } catch (OutOfMemoryError err) {
            // Ignore
        }
    }

    static void test(int level) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+HeapDumpOnOutOfMemoryError",
            "-XX:HeapDumpGzipLevel=" + level,
            "-Xmx128M",
            TestGZippedHeapDumpOnOutOfMemoryError.class.getName());

        Process proc = pb.start();
        String heapdumpFilename = "java_pid" + proc.pid() + ".hprof" + (level > 0 ? ".gz" : "");
        OutputAnalyzer output = new OutputAnalyzer(proc);
        output.stdoutShouldNotBeEmpty();
        output.shouldContain("Dumping heap to " + heapdumpFilename);
        File dump = new File(heapdumpFilename);
        Asserts.assertTrue(dump.exists() && dump.isFile(),
                "Could not find dump file " + dump.getAbsolutePath());

        HprofParser.parse(new File(heapdumpFilename));
        System.out.println("PASSED");
    }

}

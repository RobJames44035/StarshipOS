/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.startupargs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.Supplier;

import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/**
 * @test
 * @summary Start a FlightRecording with dumponexit. Verify dump exists.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.startupargs.TestDumpOnExit
 */
public class TestDumpOnExit {

    public static void main(String[] args) throws Exception {
        Path dumpPath = Paths.get(".", "dumped.jfr");

        // Test with file name relative to current directory
        testDumponExit(() -> dumpPath,
                "-Xlog:jfr=trace",
                "-XX:StartFlightRecording:filename=./dumped.jfr,dumponexit=true,settings=profile",
                "jdk.jfr.startupargs.TestDumpOnExit$TestMain"
        );
        // Test a memory recording
        testDumponExit(() -> findJFRFileInCurrentDirectory(),
                "-Xlog:jfr=trace",
                "-XX:StartFlightRecording:dumponexit=true,disk=false",
                "jdk.jfr.startupargs.TestDumpOnExit$TestMain"
        );
    }

    private static Path findJFRFileInCurrentDirectory() {
        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get("."), "*pid-*.jfr");
            Iterator<Path> pathIterator = ds.iterator();
            if (!pathIterator.hasNext()) {
                throw new RuntimeException("Could not find jfr file in current directory");
            }
            return pathIterator.next();
        } catch (IOException e) {
            throw new RuntimeException("Could not list jfr file in current directory");
        }
    }

    private static void testDumponExit(Supplier<Path> p,String... args) throws Exception, IOException {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(args);
        OutputAnalyzer output = ProcessTools.executeProcess(pb);
        System.out.println(output.getOutput());
        output.shouldHaveExitValue(0);
        Path dump = p.get();
        Asserts.assertTrue(Files.isRegularFile(dump), "No recording dumped " + dump);
        System.out.println("Dumped recording size=" + Files.size(dump));
        Asserts.assertFalse(RecordingFile.readAllEvents(dump).isEmpty(), "No events in dump");
    }

    @SuppressWarnings("unused")
    private static class TestMain {
        public static void main(String[] args) throws Exception {
            System.out.println("Hello from test main");
        }
    }

}

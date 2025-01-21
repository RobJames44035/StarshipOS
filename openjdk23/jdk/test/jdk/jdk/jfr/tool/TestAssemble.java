/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.tool;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.jfr.internal.Repository;
import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Test jfr reconstruct
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal
 * @run main/othervm jdk.jfr.tool.TestAssemble
 */
public class TestAssemble {

    @Name("Correlation")
    static class CorrelationEvent extends Event {
        int id;
    }
    private static int RECORDING_COUNT = 5;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Throwable {
        // Create some disk recordings
        Recording[] recordings = new Recording[5];
        for (int i = 0; i < RECORDING_COUNT; i++) {
            Recording r = new Recording();
            r.setToDisk(true);
            r.start();
            CorrelationEvent ce = new CorrelationEvent();
            ce.id = i;
            ce.commit();
            r.stop();
            recordings[i] = r;
        }
        Path dir = Paths.get("reconstruction-parts");
        Files.createDirectories(dir);

        long expectedCount = 0;
        for (int i = 0; i < RECORDING_COUNT; i++) {
            Path tmp = dir.resolve("chunk-part-" + i + ".jfr");
            recordings[i].dump(tmp);
            expectedCount += countEventInRecording(tmp);
        }

        Path repository = Repository.getRepository().getRepositoryPath();
        Path destinationPath = Paths.get("reconstructed.jfr");

        String directory = repository.toString();
        String destination = destinationPath.toAbsolutePath().toString();

        // Test failure
        OutputAnalyzer output = ExecuteHelper.jfr("assemble");
        output.shouldContain("too few arguments");

        output = ExecuteHelper.jfr("assemble", directory);
        output.shouldContain("too few arguments");

        output = ExecuteHelper.jfr("assemble", "not-a-directory", destination);
        output.shouldContain("directory does not exist, not-a-directory");

        output = ExecuteHelper.jfr("assemble", directory, "not-a-destination");
        output.shouldContain("filename must end with '.jfr'");

        output = ExecuteHelper.jfr("assemble", "--wrongOption", directory, destination);
        output.shouldContain("too many arguments");

        FileWriter fw = new FileWriter(destination);
        fw.write('d');
        fw.close();
        output = ExecuteHelper.jfr("assemble", directory, destination);
        output.shouldContain("already exists");
        Files.delete(destinationPath);

        // test success
        output = ExecuteHelper.jfr("assemble", directory, destination);
        System.out.println(output.getOutput());
        output.shouldContain("Finished.");

        long reconstructedCount = countEventInRecording(destinationPath);
        Asserts.assertEquals(expectedCount, reconstructedCount);
        // Cleanup
        for (int i = 0; i < RECORDING_COUNT; i++) {
            recordings[i].close();
        }
    }

    private static long countEventInRecording(Path file) throws IOException {
        Integer lastId = -1;
        try (RecordingFile rf = new RecordingFile(file)) {
            long count = 0;
            while (rf.hasMoreEvents()) {
                RecordedEvent re = rf.readEvent();
                if (re.getEventType().getName().equals("Correlation")) {
                    Integer id = re.getValue("id");
                    if (id < lastId) {
                        Asserts.fail("Expected chunk number to increase");
                    }
                    lastId = id;
                }
                count++;
            }
            return count;
        }
    }
}

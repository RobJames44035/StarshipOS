/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recorder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import jdk.jfr.Configuration;
import jdk.jfr.Recording;
import jdk.test.lib.Utils;

/**
 * @test TestStartStopRecording
 *
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recorder.TestStartStopRecording
 */
public class TestStartStopRecording {

    public static void main(String... args) throws Exception {
        Configuration defaultConfig = Configuration.getConfiguration("default");
        // Memory
        Recording inMemory = new Recording(defaultConfig);
        inMemory.setToDisk(false);

        inMemory.start();

        Path memoryFile = Utils.createTempFile("start-stop-memory-recording", ".jfr");
        inMemory.dump(memoryFile);
        assertValid(memoryFile, "Not a valid memory file.");
        inMemory.stop();
        inMemory.close();
        // Disk
        Recording toDisk = new Recording(defaultConfig);
        toDisk.setToDisk(true);

        toDisk.start();
        toDisk.stop();
        Path diskFile = Utils.createTempFile("start-stop-disk-recording", ".jfr");
        toDisk.dump(diskFile);
        assertValid(diskFile, "Not a valid disk file.");
        toDisk.close();
    }

    private static void assertValid(Path path, String message) throws IOException {
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            throw new AssertionError(message + " Could not find file " + path);
        }
        if (Files.size(path) == 0) {
            throw new AssertionError(message + " File size = 0 for " + path);
        }
    }
}

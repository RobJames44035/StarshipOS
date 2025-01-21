/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.dump;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.VoidFunction;

/**
 * @test
 * @summary Test copyTo and parse file
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.dump.TestDumpInvalid
 */
public class TestDumpInvalid {

    public static void main(String[] args) throws Throwable {
        Recording r = new Recording();
        r.enable(EventNames.OSInformation);
        r.start();
        r.stop();

        verifyNullPointer(()->{r.dump(null);}, "No NullPointerException");

        Path pathNotExists = Paths.get(".", "dirNotExists", "my.jfr");
        verifyFileNotFound(()->{r.dump(pathNotExists);}, "No Exception with missing dir");

        Path pathEmpty = Paths.get("");
        verifyFileNotFound(()->{r.dump(pathEmpty);}, "No Exception with empty path");

        Path pathDir = Paths.get(".", "newdir");
        Files.createDirectory(pathDir);
        verifyFileNotFound(()->{r.dump(pathDir);}, "No Exception with dir");

        // Verify that copyTo() works after all failed attempts.
        Path pathOk = Paths.get(".", "newdir", "my.jfr");
        r.dump(pathOk);
        Asserts.assertTrue(Files.exists(pathOk), "Recording file does not exist: " + pathOk);
        Asserts.assertFalse(RecordingFile.readAllEvents(pathOk).isEmpty(), "No events found");

        r.close();
    }

    private static void verifyFileNotFound(VoidFunction f, String msg) throws Throwable {
        CommonHelper.verifyException(f, msg, IOException.class);
    }

    private static void verifyNullPointer(VoidFunction f, String msg) throws Throwable {
        CommonHelper.verifyException(f, msg, NullPointerException.class);
    }

}

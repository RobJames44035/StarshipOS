/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.destination;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.Recording;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.FileHelper;

/**
 * @test
 * @summary Set destination to a read-only file. Expects exception.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.destination.TestDestFileReadOnly
 */
public class TestDestFileReadOnly {

    public static void main(String[] args) throws Throwable {
        Path dest = FileHelper.createReadOnlyFile(Paths.get(".", "readonly.txt"));
        System.out.println("dest=" + dest.toFile().getAbsolutePath());
        if (!FileHelper.isReadOnlyPath(dest)) {
            System.out.println("Failed to create a read-only file. Test ignored.");
            return;
        }

        Recording r = new Recording();
        r.setToDisk(true);
        try {
            r.setDestination(dest);
            Asserts.fail("No exception when destination is read-only");
        } catch (IOException e) {
            // Expected exception
        }
        r.close();
    }

}

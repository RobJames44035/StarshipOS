/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.management.jfr.FlightRecorderMXBean;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.FileHelper;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestCopyToReadOnlyDir
 */
public class TestCopyToReadOnlyDir {
    public static void main(String[] args) throws Throwable {
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();

        long recId = bean.newRecording();
        bean.startRecording(recId);
        SimpleEventHelper.createEvent(1);
        bean.stopRecording(recId);

        Path readOnlyDir = FileHelper.createReadOnlyDir(Paths.get(".", "readOnlyDir"));
        System.out.println("readOnlyDir=" + readOnlyDir.toString());
        Asserts.assertTrue(readOnlyDir.toFile().isDirectory(), "Could not create directory. Test error");
        if (!FileHelper.isReadOnlyPath(readOnlyDir)) {
            System.out.println("Failed to create read-only dir. Maybe running as root? Skipping test");
            return;
        }

        Path file = Paths.get(readOnlyDir.toString(), "my.jfr");
        System.out.println("file=" + file.toString());
        try {
            bean.copyTo(recId, file.toString());
            Asserts.fail("Should be able to dump to read only file");
        } catch (AccessDeniedException e) {
            // ok as expected
        }

        bean.closeRecording(recId);
    }
}

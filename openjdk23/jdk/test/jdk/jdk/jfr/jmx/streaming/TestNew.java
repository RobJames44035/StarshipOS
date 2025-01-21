/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.jmx.streaming;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import jdk.management.jfr.FlightRecorderMXBean;
import jdk.management.jfr.RemoteRecordingStream;

/**
 * @test
 * @key jfr
 * @summary Test constructors of RemoteRecordingStream
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.streaming.TestNew
 */
public class TestNew {

    private final static ObjectName MXBEAN = createObjectName();

    public static void main(String... args) throws Exception {
        testNullArguments();
        testMissingDirectory();
        testNotDirectory();
        testDefaultDIrectory();
        TestUserDefinedDirectory();

        testMissingFlightRecorderMXBean();
    }

    private static void TestUserDefinedDirectory() throws IOException {
        Path p = Paths.get("user-repository-" + System.currentTimeMillis());
        Files.createDirectory(p);
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        try (RemoteRecordingStream s = new RemoteRecordingStream(conn, p)) {
            // success
        }
    }

    private static void testDefaultDIrectory() throws IOException {
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        try (RemoteRecordingStream s = new RemoteRecordingStream(conn)) {
            // success
        }
    }

    private static void testNotDirectory() throws Exception {
        Path p = Paths.get("file.txt");
        RandomAccessFile raf = new RandomAccessFile(p.toFile(), "rw");
        raf.close();
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        try (var s = new RemoteRecordingStream(conn, p)) {
            throw new Exception("Expected IOException");
        } catch (IOException ioe) {
            if (!ioe.getMessage().contains("Download location must be a directory")) {
                throw new Exception("Unexpected message " + ioe.getMessage());
            }
        }
    }

    private static void testMissingDirectory() throws Exception {
        Path p = Paths.get("/missing");
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        try (var s = new RemoteRecordingStream(conn, p)) {
            throw new Exception("Expected IOException");
        } catch (IOException ioe) {
            if (!ioe.getMessage().contains("Download directory doesn't exist")) {
                throw new Exception("Unexpected message " + ioe.getMessage());
            }
        }
    }

    private static void testNullArguments() throws Exception {
        try (var s = new RemoteRecordingStream(null)) {
            throw new Exception("Expected NullPointerException");
        } catch (NullPointerException npe) {
            // as expected
        }
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        try (var s = new RemoteRecordingStream(conn, null)) {
            throw new Exception("Expected NullPointerException");
        } catch (NullPointerException npe) {
            // as expected
        }
    }

    private static void testMissingFlightRecorderMXBean() throws Exception {

        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        conn.unregisterMBean(MXBEAN);
        try (var s = new RemoteRecordingStream(conn)) {
            throw new Exception("Expected IOException");
        } catch (IOException npe) {
            // as expected
        }
    }

    private static ObjectName createObjectName() {
        try {
            return new ObjectName(FlightRecorderMXBean.MXBEAN_NAME);
        } catch (Exception e) {
            throw new InternalError("Unexpected exception", e);
        }
    }
}

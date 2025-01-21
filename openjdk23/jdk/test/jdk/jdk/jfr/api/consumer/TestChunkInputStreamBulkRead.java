/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test TestChunkInputStreamBulkRead
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestChunkInputStreamBulkRead
 */
package jdk.jfr.api.consumer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.Recording;
import jdk.test.lib.Asserts;

public class TestChunkInputStreamBulkRead {

    public static void main(String[] args) throws Exception {
        try (Recording r = new Recording()) {
            r.start();
            try (Recording s = new Recording()) {
                s.start();
                s.stop();
            }
            r.stop();
            try (InputStream stream = r.getStream(null, null);
                 ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                long read = stream.transferTo(output);
                System.out.printf("Read %d bytes from JFR stream%n", read);
                Asserts.assertEquals(r.getSize(), read);

                byte[] actual = output.toByteArray();
                Asserts.assertEqualsByteArray(r.getStream(null, null).readAllBytes(), actual);

                Path dumpFile = Paths.get("recording.jfr").toAbsolutePath().normalize();
                r.dump(dumpFile);
                System.out.printf("Dumped recording to %s (%d bytes)%n", dumpFile, Files.size(dumpFile));
                Asserts.assertEqualsByteArray(Files.readAllBytes(dumpFile), actual);
            }
        }
    }
}

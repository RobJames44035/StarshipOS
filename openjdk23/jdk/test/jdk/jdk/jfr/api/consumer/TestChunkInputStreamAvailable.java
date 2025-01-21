/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test TestChunkInputStreamAvailable
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestChunkInputStreamAvailable
 */
package jdk.jfr.api.consumer;

import java.io.InputStream;

import jdk.jfr.Recording;
import jdk.test.lib.Asserts;

public class TestChunkInputStreamAvailable {

    public static void main(String[] args) throws Exception {
        try (Recording r = new Recording()) {
            r.start();
            try (Recording s = new Recording()) {
                s.start();
                s.stop();
            }
            r.stop();
            try (InputStream stream = r.getStream(null, null)) {
                int left = stream.available();
                Asserts.assertEquals(r.getSize(), (long) left);
                while (stream.read() != -1) {
                    left--;
                    Asserts.assertEquals(left, stream.available());
                }
                Asserts.assertEquals(0, left);
            }
        }
    }
}

/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.micro.bench.java.util;

import org.openjdk.jmh.annotations.*;
import java.util.*;

@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class Base64VarLenDecode {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Trial)
        public void doSetupTrial() {
            ran = new Random(10101); // fixed seed for repeatability
            encoder = Base64.getEncoder();
            decoder = Base64.getDecoder();
        }

        @Setup(Level.Invocation)
        public void doSetupInvocation() {
            bin_src_len = 8 + ran.nextInt(20000);
            base64_len = ((bin_src_len + 2) / 3) * 4;
            unencoded = new byte[bin_src_len];
            encoded = new byte[base64_len];
            decoded = new byte[bin_src_len];
            ran.nextBytes(unencoded);
            encoder.encode(unencoded, encoded);
        }

        @TearDown(Level.Invocation)
        public void doTearDownInvocation() {
            // This isn't really a teardown.  It's a check for correct functionality.
            // Each iteration should produce a correctly decoded buffer that's equal
            // to the unencoded data.
            if (!Arrays.equals(unencoded, decoded)) {
                System.out.println("Original data and decoded data are not equal!");
                for (int j = 0; j < unencoded.length; j++) {
                    if (unencoded[j] != decoded[j]) {
                        System.out.format("%06x: %02x %02x\n", j, unencoded[j], decoded[j]);
                    }
                }
                System.exit(1);
            }
        }

        public Random ran;
        public Base64.Encoder encoder;
        public Base64.Decoder decoder;
        public int bin_src_len;
        public int base64_len;
        public byte[] unencoded;
        public byte[] encoded;
        public byte[] decoded;
    }

    @Benchmark
    public void decodeMethod(MyState state) {
       state.decoder.decode(state.encoded, state.decoded);
    }
}

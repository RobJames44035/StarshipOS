/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.GCMParameterSpec;

/**
 * This performance tests runs AES/GCM encryption and decryption
 * using input and output byte[] buffers with single and multi-part testing.
 */

public class AESGCMBench extends BenchBase {

    @Param({"128", "192", "256"})
    int keyLength;

    public static final int IV_MODULO = 16;

    public AlgorithmParameterSpec getNewSpec() {
        iv_index = (iv_index + 1) % IV_MODULO;
        return new GCMParameterSpec(128, iv, iv_index, IV_MODULO);
    }

    @Setup
    public void setup() throws Exception {
        init("AES/GCM/NoPadding", keyLength);
    }
}

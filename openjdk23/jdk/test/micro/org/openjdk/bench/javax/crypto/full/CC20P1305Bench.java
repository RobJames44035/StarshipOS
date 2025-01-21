/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * This performance tests runs ChaCha20-Poly1305 encryption and decryption
 * using input and output byte[] buffers with single and multi-part testing.
 */

public class CC20P1305Bench extends BenchBase {

    public static final int IV_MODULO = 12;

    public AlgorithmParameterSpec getNewSpec() {
        iv_index = (iv_index + 1) % IV_MODULO;
        return new IvParameterSpec(iv, iv_index, IV_MODULO);
    }

    @Setup
    public void setup() throws Exception {
        init("ChaCha20-Poly1305/None/NoPadding", keyLength);
    }
}

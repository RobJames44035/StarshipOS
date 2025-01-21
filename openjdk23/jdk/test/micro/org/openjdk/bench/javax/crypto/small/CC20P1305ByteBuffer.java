/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.small;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * This small performance tests runs ChaCha20-Poly1305 encryption and decryption
 * using heap and direct ByteBuffers for input and output buffers with single
 * and multi-part operations.  Only 1024 plaintext data length is tested.
 */

public class CC20P1305ByteBuffer extends
    org.openjdk.bench.javax.crypto.full.CC20P1305ByteBuffer {

    @Param({"1024"})
    int dataSize;

    @Setup
    public void setup() throws Exception {
        init("ChaCha20-Poly1305/None/NoPadding", 256, dataSize);
    }

}

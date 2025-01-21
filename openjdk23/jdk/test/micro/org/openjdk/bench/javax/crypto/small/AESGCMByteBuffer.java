/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.small;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.GCMParameterSpec;

/**
 * This small performance tests runs AES/GCM encryption and decryption
 * using heap and direct ByteBuffers for input and output buffers with single
 * and multi-part operations.  Only 1024 plaintext data length is tested.
 */

public class AESGCMByteBuffer extends
    org.openjdk.bench.javax.crypto.full.AESGCMByteBuffer {

    @Param({"128"})
    int keyLength;

    @Param({"1024"})
    int dataSize;

    @Setup
    public void setup() throws Exception {
        init("AES/GCM/NoPadding", keyLength, dataSize);
    }
}
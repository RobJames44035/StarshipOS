/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestBench extends CryptoBase {

    public static final int SET_SIZE = 128;

    @Param({"MD5", "SHA", "SHA-256", "SHA-384", "SHA-512"})
    private String algorithm;

    /*
     * Note: dataSize value shouldn't be small unless you want to evaluate MessageDigest.getInstance performance.
     * Small value causes large impact of MessageDigest.getInstance including lock contention in multi-threaded
     * execution.
     */
    @Param({""+1024*1024})
    int dataSize;

    private byte[][] data;
    int index = 0;


    @Setup
    public void setup() {
        setupProvider();
        data = fillRandom(new byte[SET_SIZE][dataSize]);
    }

    @Benchmark
    public byte[] digest() throws NoSuchAlgorithmException {
        MessageDigest md = (prov == null) ? MessageDigest.getInstance(algorithm) : MessageDigest.getInstance(algorithm, prov);
        byte[] d = data[index];
        index = (index +1) % SET_SIZE;
        return md.digest(d);
    }

}

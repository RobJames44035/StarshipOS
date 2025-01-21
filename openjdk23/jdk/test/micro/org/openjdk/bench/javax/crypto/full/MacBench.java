/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MacBench extends CryptoBase {

    public static final int SET_SIZE = 128;

    @Param({"HmacMD5", "HmacSHA1", "HmacSHA256", "HmacSHA512"})
    private String algorithm;

    @Param({"128", "1024"})
    int dataSize;

    private byte[][] data;
    private Mac mac;
    int index = 0;

    @Setup
    public void setup() throws NoSuchAlgorithmException, InvalidKeyException {
        setupProvider();
        mac = (prov == null) ? Mac.getInstance(algorithm) : Mac.getInstance(algorithm, prov);
        mac.init(KeyGenerator.getInstance(algorithm).generateKey());
        data = fillRandom(new byte[SET_SIZE][dataSize]);
    }

    @Benchmark
    public byte[] mac() throws NoSuchAlgorithmException {
        byte[] d = data[index];
        index = (index + 1) % SET_SIZE;
        return mac.doFinal(d);
    }

}

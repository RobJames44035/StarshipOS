/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

public class SecureRandomBench extends CryptoBase {

    @Param({"NativePRNG", "SHA1PRNG"})
    private String algorithm;

    @Param({"64"})
    int dataSize;

    @Param({"true", "false"})
    // if shared - use the single SecureRandom instance for all threads
    // otherwise - each thread uses its own SecureRandom instance
    boolean shared;

    private byte[] bytes;
    private SecureRandom rnd;

    private static SecureRandom sharedRnd;

    private static synchronized SecureRandom getSharedInstance(String algorithm, Provider prov) throws NoSuchAlgorithmException {
        if (sharedRnd == null) {
            sharedRnd = (prov == null) ? SecureRandom.getInstance(algorithm) : SecureRandom.getInstance(algorithm, prov);
        }
        return sharedRnd;
    }

    @Setup
    public void setup() throws NoSuchAlgorithmException {
        setupProvider();
        bytes = new byte[dataSize];
        if (shared) {
            rnd = getSharedInstance(algorithm, prov);
        } else {
            rnd = (prov == null) ? SecureRandom.getInstance(algorithm) : SecureRandom.getInstance(algorithm, prov);
        }
    }

    @Benchmark
    public byte[] nextBytes() {
        rnd.nextBytes(bytes);
        return bytes;
    }

}

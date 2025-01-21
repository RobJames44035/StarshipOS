/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyPairGeneratorBench extends CryptoBase {

    private KeyPairGenerator generator;

    @Param({"DSA", "DiffieHellman"})
    private String algorithm;

    @Param({"1024", "2048"})
    private int keyLength;

    @Setup
    public void setup() throws NoSuchAlgorithmException {
        setupProvider();
        generator = (prov == null) ? KeyPairGenerator.getInstance(algorithm)
                                : KeyPairGenerator.getInstance(algorithm, prov);
        generator.initialize(keyLength);
    }

    @Benchmark
    public KeyPair generateKeyPair() {
        return generator.generateKeyPair();
    }

    public static class RSA extends KeyPairGeneratorBench {

        @Param({"RSA"})
        private String algorithm;

        @Param({"1024", "2048", "3072", "4096"})
        private int keyLength;
    }

    public static class RSASSAPSS extends KeyPairGeneratorBench {

        @Param({"RSASSA-PSS"})
        private String algorithm;

        @Param({"1024", "2048", "3072", "4096"})
        private int keyLength;
    }

    public static class EC extends KeyPairGeneratorBench {

        @Param({"EC"})
        private String algorithm;

        @Param({"256", "384", "521"})
        private int keyLength;
    }

    public static class EdDSA extends KeyPairGeneratorBench {

        @Param({"EdDSA"})
        private String algorithm;

        @Param({"255", "448"})
        private int keyLength;
    }

    public static class XDH extends KeyPairGeneratorBench {

        @Param({"XDH"})
        private String algorithm;

        @Param({"255", "448"})
        private int keyLength;
    }
}

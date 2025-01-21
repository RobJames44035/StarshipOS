/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import java.util.concurrent.TimeUnit;


import java.security.Key;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;


public class AESKeyWrapBench extends CryptoBase {

    @Param({"AES/KW/NoPadding" , "AES/KW/PKCS5Padding", "AES/KWP/NoPadding"})
    private String algorithm;

    @Param({"128"})
    private int keyLength;

    @Param({"16", "24"})
    private int dataSize;

    SecretKeySpec toBeWrappedKey;
    byte[] wrappedKey;
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    SecretKeySpec ks;

    @Setup
    public void setup() throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException {
        setupProvider();

        byte[] keystring = fillSecureRandom(new byte[keyLength / 8]);
        ks = new SecretKeySpec(keystring, "AES");
        encryptCipher = makeCipher(prov, algorithm);
        encryptCipher.init(Cipher.WRAP_MODE, ks);
        decryptCipher = makeCipher(prov, algorithm);
        decryptCipher.init(Cipher.UNWRAP_MODE, ks);
        byte[] data = fillRandom(new byte[dataSize]);
        toBeWrappedKey = new SecretKeySpec(data, "Custom");
        wrappedKey = encryptCipher.wrap(toBeWrappedKey);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public byte[] wrap() throws InvalidKeyException, IllegalBlockSizeException {
        return encryptCipher.wrap(toBeWrappedKey);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public Key unwrap() throws InvalidKeyException, NoSuchAlgorithmException {
        return decryptCipher.unwrap(wrappedKey, "Custom", Cipher.SECRET_KEY);
    }
}

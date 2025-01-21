/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Random;

public class SignatureBench extends CryptoBase {

    public static final int SET_SIZE = 128;

    @Param({"SHA256withDSA"})
    private String algorithm;

    @Param({"1024", "16384"})
    int dataSize;

    @Param({"1024"})
    private int keyLength;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private byte[][] data;
    private byte[][] signedData;
    int index;


    private String getKeyPairGeneratorName() {
        int withIndex = algorithm.lastIndexOf("with");
        if (withIndex < 0) {
            return algorithm;
        }
        String tail = algorithm.substring(withIndex + 4);
        return "ECDSA".equals(tail) ? "EC" : tail;
    }

    @Setup()
    public void setup() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        setupProvider();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(getKeyPairGeneratorName());
        kpg.initialize(keyLength);
        KeyPair keys = kpg.generateKeyPair();
        this.privateKey = keys.getPrivate();
        this.publicKey = keys.getPublic();
        data = fillRandom(new byte[SET_SIZE][dataSize]);
        signedData = new byte[data.length][];
        for (int i = 0; i < data.length; i++) {
            signedData[i] = sign(data[i]);
        }

    }

    public byte[] sign(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = (prov == null) ? Signature.getInstance(algorithm) : Signature.getInstance(algorithm, prov);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    @Benchmark
    public byte[] sign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] d = data[index];
        index = (index + 1) % SET_SIZE;
        return sign(d);
    }

    @Benchmark
    public boolean verify() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = (prov == null) ? Signature.getInstance(algorithm) : Signature.getInstance(algorithm, prov);
        signature.initVerify(publicKey);
        byte[] d = data[index];
        byte[] s = signedData[index];
        index = (index + 1) % SET_SIZE;
        signature.update(d);
        return signature.verify(s);
    }

    public static class RSA extends SignatureBench {

        @Param({"MD5withRSA", "SHA256withRSA"})
        private String algorithm;

        @Param({"1024", "2048", "3072"})
        private int keyLength;

    }

    public static class ECDSA extends SignatureBench {

        @Param({"SHA256withECDSA"})
        private String algorithm;

        @Param({"256"})
        private int keyLength;

    }

    public static class EdDSA extends SignatureBench {

        @Param({"EdDSA"})
        private String algorithm;

        @Param({"255", "448"})
        private int keyLength;

    }

}

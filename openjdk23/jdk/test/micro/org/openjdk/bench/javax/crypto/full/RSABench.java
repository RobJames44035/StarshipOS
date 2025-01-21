/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSABench extends CryptoBase {

    public static final int SET_SIZE = 128;

    @Param({"RSA/ECB/NoPadding", "RSA/ECB/PKCS1Padding", "RSA/ECB/OAEPPadding"})
    protected String algorithm;

    @Param({"32"})
    protected int dataSize;

    @Param({"1024", "2048", "3072"})
    protected int keyLength;


    private byte[][] data;
    private byte[][] encryptedData;

    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private int index = 0;

    @Setup()
    public void setup() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        setupProvider();
        data = fillRandom(new byte[SET_SIZE][dataSize]);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(keyLength);
        KeyPair keyPair = kpg.generateKeyPair();

        encryptCipher = makeCipher(prov, algorithm);
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        decryptCipher = makeCipher(prov, algorithm);
        decryptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        encryptedData = fillEncrypted(data, encryptCipher);

    }

    @Benchmark
    public byte[] encrypt() throws BadPaddingException, IllegalBlockSizeException {
        byte[] d = data[index];
        index = (index +1) % SET_SIZE;
        return encryptCipher.doFinal(d);
    }

    @Benchmark
    public byte[] decrypt() throws BadPaddingException, IllegalBlockSizeException {
        byte[] e = encryptedData[index];
        index = (index +1) % SET_SIZE;
        return decryptCipher.doFinal(e);
    }

    public static class Extra extends RSABench {

        @Param({"RSA/ECB/NoPadding", "RSA/ECB/PKCS1Padding"})
        private String algorithm;

        @Param({"214"})
        private int dataSize;

        @Param({"2048", "3072"})
        private int keyLength;

    }

}

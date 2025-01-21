/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;
import java.security.GeneralSecurityException;
import org.openjdk.jmh.annotations.Fork;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

@Fork(jvmArgsAppend = {"-Xms20g", "-Xmx20g", "-XX:+UseZGC"})
public class AESBench extends CryptoBase {

    public static final int SET_SIZE = 8;

    @Param({"AES/ECB/NoPadding", "AES/ECB/PKCS5Padding", "AES/CBC/NoPadding", "AES/CBC/PKCS5Padding"})
    private String algorithm;

    @Param({"128", "192", "256"})
    private int keyLength;

    @Param({"" + 16 * 1024})
    private int dataSize;

    byte[][] data;
    byte[][] encryptedData;
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private byte[] outBuffer;
    int index = 0;

    @Setup
    public void setup() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
        setupProvider();
        byte[] keystring = fillSecureRandom(new byte[keyLength / 8]);
        SecretKeySpec ks = new SecretKeySpec(keystring, "AES");
        encryptCipher = makeCipher(prov, algorithm);
        encryptCipher.init(Cipher.ENCRYPT_MODE, ks);
        decryptCipher = makeCipher(prov, algorithm);
        decryptCipher.init(Cipher.DECRYPT_MODE, ks, encryptCipher.getParameters());
        data = fillRandom(new byte[SET_SIZE][dataSize]);
        encryptedData = fillEncrypted(data, encryptCipher);
        outBuffer = new byte[dataSize + 128]; // extra space for tag, etc
    }

    @Benchmark
    public byte[] encrypt() throws BadPaddingException, IllegalBlockSizeException {
        byte[] d = data[index];
        index = (index +1) % SET_SIZE;
        return encryptCipher.doFinal(d);
    }

    @Benchmark
    public void encrypt2(Blackhole bh) throws GeneralSecurityException {
        byte[] d = data[index];
        index = (index +1) % SET_SIZE;
        bh.consume(encryptCipher.doFinal(d, 0, d.length, outBuffer));
    }

    @Benchmark
    public byte[] decrypt() throws BadPaddingException, IllegalBlockSizeException {
        byte[] e = encryptedData[index];
        index = (index +1) % SET_SIZE;
        return decryptCipher.doFinal(e);
    }

    @Benchmark
    public void decrypt2(Blackhole bh) throws GeneralSecurityException {
        byte[] e = encryptedData[index];
        index = (index +1) % SET_SIZE;
        bh.consume(decryptCipher.doFinal(e, 0, e.length, outBuffer));
    }

}

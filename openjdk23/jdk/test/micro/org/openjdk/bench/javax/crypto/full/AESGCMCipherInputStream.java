/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * This performance test runs AES/GCM encryption and decryption using CipherInputStream.
 *
 * This test rotates the IV and creates a new GCMParameterSpec for each encrypt
 * benchmark operation
 */

public class AESGCMCipherInputStream extends CryptoBase {

    @Param({"128"})
    private int keyLength;

    @Param({"16384", "1048576"})
    private int dataSize;

    byte[] encryptedData;
    byte[] in;
    ByteArrayOutputStream out;
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    SecretKeySpec ks;
    GCMParameterSpec gcm_spec;
    byte[] iv;

    private static final int IV_BUFFER_SIZE = 32;
    private static final int IV_MODULO = IV_BUFFER_SIZE - 16;
    int iv_index = 0;

    private int next_iv_index() {
        int r = iv_index;
        iv_index = (iv_index + 1) % IV_MODULO;
        return r;
    }

    @Setup
    public void setup() throws Exception {
        setupProvider();

        // Setup key material
        byte[] keystring = fillSecureRandom(new byte[keyLength / 8]);
        ks = new SecretKeySpec(keystring, "AES");
        iv = fillSecureRandom(new byte[IV_BUFFER_SIZE]);
        gcm_spec = new GCMParameterSpec(96, iv, next_iv_index(), 16);

        // Setup Cipher classes
        encryptCipher = makeCipher(prov, "AES/GCM/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, ks, gcm_spec);
        decryptCipher = makeCipher(prov, "AES/GCM/NoPadding");
        decryptCipher.init(Cipher.DECRYPT_MODE, ks,
            encryptCipher.getParameters().
                getParameterSpec(GCMParameterSpec.class));

        // Setup input/output buffers
        in = fillRandom(new byte[dataSize]);
        encryptedData = new byte[encryptCipher.getOutputSize(in.length)];
        out = new ByteArrayOutputStream(encryptedData.length);
        encryptCipher.doFinal(in, 0, in.length, encryptedData, 0);
    }

    @Benchmark
    public byte[] encrypt() throws Exception {
        out.reset();
        gcm_spec = new GCMParameterSpec(96, iv, next_iv_index(), 16);
        encryptCipher.init(Cipher.ENCRYPT_MODE, ks, gcm_spec);
        ByteArrayInputStream fin = new ByteArrayInputStream(in);
        InputStream cin = new CipherInputStream(fin, encryptCipher);

        cin.transferTo(out);
        return out.toByteArray();
    }

    @Benchmark
    public byte[] decrypt() throws Exception {
        out.reset();
        decryptCipher.init(Cipher.DECRYPT_MODE, ks,
                encryptCipher.getParameters().
                        getParameterSpec(GCMParameterSpec.class));
        ByteArrayInputStream fin = new ByteArrayInputStream(encryptedData);
        InputStream cin = new CipherInputStream(fin, decryptCipher);

        cin.transferTo(out);
        return out.toByteArray();
    }
}

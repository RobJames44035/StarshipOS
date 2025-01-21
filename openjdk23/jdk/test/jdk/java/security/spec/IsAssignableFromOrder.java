/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import sun.security.util.CurveDB;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.PSSParameterSpec;

/**
 * @test
 * @bug 8279800
 * @modules java.base/sun.security.util
 * @summary isAssignableFrom checks in AlgorithmParametersSpi.engineGetParameterSpec appear to be backwards
 */

public class IsAssignableFromOrder {

    public static void main(String[] args) throws Exception {

        // AlgorithmParameters
        testAlgSpec("AES", new IvParameterSpec(new byte[16]));
        testAlgSpec("ChaCha20-Poly1305", new IvParameterSpec(new byte[12]));
        testAlgSpec("DiffieHellman", new DHParameterSpec(BigInteger.ONE, BigInteger.TWO));
        testAlgSpec("GCM", new GCMParameterSpec(96, new byte[16]));
        testAlgSpec("OAEP", OAEPParameterSpec.DEFAULT);
        testAlgSpec("PBEWithSHA1AndDESede", new PBEParameterSpec(
                "saltsalt".getBytes(StandardCharsets.UTF_8), 10000));
        testAlgSpec("PBEWithHmacSHA256AndAES_256", new PBEParameterSpec(
                "saltsalt".getBytes(StandardCharsets.UTF_8), 10000,
                new IvParameterSpec(new byte[16])));
        testAlgSpec("RC2", new RC2ParameterSpec(256, new byte[32]));
        testAlgSpec("DSA", new DSAParameterSpec(
                BigInteger.ONE, BigInteger.TWO, BigInteger.TEN));
        testAlgSpec("RSASSA-PSS", PSSParameterSpec.DEFAULT);
        testAlgSpec("EC", new ECGenParameterSpec("secp256r1"));
        testAlgSpec("EC", CurveDB.lookup("secp256r1"),
                ECParameterSpec.class, AlgorithmParameterSpec.class);

        // SecretKeyFactory
        var spec = new PBEKeySpec(
                "password".toCharArray(),
                "saltsalt".getBytes(StandardCharsets.UTF_8),
                10000,
                32);

        testKeySpec("PBE", spec, PBEKeySpec.class);
        testKeySpec("PBEWithHmacSHA256AndAES_256", spec, PBEKeySpec.class);
        testKeySpec("PBKDF2WithHmacSHA1", spec, PBEKeySpec.class);

        testKeySpec("DES", new SecretKeySpec(new byte[8], "DES"), DESKeySpec.class);
        testKeySpec("DESede", new SecretKeySpec(new byte[24], "DESede"), DESedeKeySpec.class);
    }


    static void testAlgSpec(String algorithm, AlgorithmParameterSpec spec,
            Class<? extends AlgorithmParameterSpec>... classes) throws Exception {
        System.out.println(algorithm);
        var ap1 = AlgorithmParameters.getInstance(algorithm);
        ap1.init(spec);
        var ap2 = AlgorithmParameters.getInstance(algorithm);
        ap2.init(ap1.getEncoded());
        if (classes.length == 0) {
            classes = new Class[]{spec.getClass(), AlgorithmParameterSpec.class};
        }
        for (var c : classes) {
            ap1.getParameterSpec(c);
            ap2.getParameterSpec(c);
        }
    }

    static void testKeySpec(String algorithm, KeySpec spec, Class<?> clazz)
            throws Exception {
        System.out.println(algorithm);
        var kf = SecretKeyFactory.getInstance(algorithm);
        var key = kf.generateSecret(spec);
        kf.getKeySpec(key, KeySpec.class);
        kf.getKeySpec(key, clazz);
    }
}

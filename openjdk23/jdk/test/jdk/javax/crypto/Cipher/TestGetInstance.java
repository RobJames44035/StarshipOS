/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4898428
 * @summary test that the new getInstance() implementation works correctly
 * @author Andreas Sterbenz
 * @run main TestGetInstance DES PBEWithMD5AndTripleDES
 * @run main TestGetInstance AES PBEWithHmacSHA1AndAES_128
 */

import java.security.*;
import java.security.spec.*;
import java.util.Locale;

import javax.crypto.*;

public class TestGetInstance {

    private static void same(Provider p1, Provider p2) throws Exception {
        if (p1 != p2) {
           throw new Exception("not same object");
        }
    }

    public static void main(String[] args) throws Exception {
        String algo = args[0];
        String algoLC = algo.toLowerCase(Locale.ROOT);
        String pbeAlgo = args[1];
        Provider p = Security.getProvider(
                System.getProperty("test.provider.name", "SunJCE"));

        Cipher c;

        c = Cipher.getInstance(pbeAlgo);
        same(p, c.getProvider());

        c = Cipher.getInstance(algoLC,
                System.getProperty("test.provider.name", "SunJCE"));
        same(p, c.getProvider());
        c = Cipher.getInstance(algoLC + "/cbc/pkcs5padding",
                System.getProperty("test.provider.name", "SunJCE"));
        same(p, c.getProvider());

        c = Cipher.getInstance(algoLC, p);
        same(p, c.getProvider());
        c = Cipher.getInstance(algoLC + "/cbc/pkcs5padding", p);
        same(p, c.getProvider());

        try {
            c = Cipher.getInstance(algo + "/XYZ/PKCS5Padding");
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance(algo + "/XYZ/PKCS5Padding",
                    System.getProperty("test.provider.name", "SunJCE"));
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance(algo + "/XYZ/PKCS5Padding", p);
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        try {
            c = Cipher.getInstance(algo + "/CBC/XYZPadding");
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance(algo + "/CBC/XYZPadding",
                    System.getProperty("test.provider.name", "SunJCE"));
            throw new AssertionError();
        } catch (NoSuchPaddingException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance(algo + "/CBC/XYZPadding", p);
            throw new AssertionError();
        } catch (NoSuchPaddingException e) {
            System.out.println(e);
        }

        try {
            c = Cipher.getInstance("foo");
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance("foo",
                    System.getProperty("test.provider.name", "SunJCE"));
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance("foo", p);
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        try {
            c = Cipher.getInstance("foo",
                    System.getProperty("test.provider.name", "SUN"));
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance("foo", Security.getProvider(
                    System.getProperty("test.provider.name", "SUN")));
            throw new AssertionError();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        try {
            c = Cipher.getInstance("foo", "bar");
            throw new AssertionError();
        } catch (NoSuchProviderException e) {
            System.out.println(e);
        }

        System.out.println("All Tests ok");
    }
}

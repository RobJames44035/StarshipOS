/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4894125 7054918 8130181
 * @library ../testlibrary
 * @summary test that failover for KeyPairGenerator works
 * @author Andreas Sterbenz
 */

import java.util.*;

import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;

public class Failover {

    public static void main(String[] args) throws Exception {
        ProvidersSnapshot snapshot = ProvidersSnapshot.create();
        try {
            main0(args);
        } finally {
            snapshot.restore();
        }
    }

    public static void main0(String[] args) throws Exception {
        Security.insertProviderAt(new ProviderFail(), 1);
        Security.addProvider(new ProviderPass());
        System.out.println(Arrays.asList(Security.getProviders()));

        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance("FOO");
        kpg.generateKeyPair();
        kpg.generateKeyPair();

        kpg = KeyPairGenerator.getInstance("FOO");
        kpg.initialize(1024);
        kpg.initialize(1024);
        kpg.initialize(null, null);
        kpg.generateKeyPair();

        kpg = KeyPairGenerator.getInstance("FOO");
        kpg.initialize(null, null);
        kpg.generateKeyPair();

        kpg = KeyPairGenerator.getInstance("FOO");
        kpg.initialize(512);
        kpg.generateKeyPair();
        kpg.generateKeyPair();

        // the SUN DSA KeyPairGenerator implementation extends
        // KeyPairGenerator (in order to implement
        // java.security.interfaces.DSAKeyPairGenerator)
        // failover cannot work
        kpg = KeyPairGenerator.getInstance("DSA");
        try {
            kpg.initialize(1024);
            throw new Exception("no exception");
        } catch (InvalidParameterException e) {
            System.out.println(e);
        }

        KeyPair kp;
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        kp = kpg.generateKeyPair();
        System.out.println(kp.getPublic());

        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(768);
        kp = kpg.generateKeyPair();
        System.out.println(kp.getPublic());

        kpg = KeyPairGenerator.getInstance("RSA");
        kp = kpg.generateKeyPair();
        System.out.println(kp.getPublic());

        kpg = KeyPairGenerator.getInstance("RSA");
        try {
            kpg.initialize(128);
            throw new Exception("no exception");
        } catch (InvalidParameterException e) {
            System.out.println(e);
        }

    }

    private static class ProviderPass extends Provider {
        ProviderPass() {
            super("Pass", "1.0", "Pass");
            put("KeyPairGenerator.FOO" , "Failover$KeyPairGeneratorPass");
        }
    }

    private static class ProviderFail extends Provider {
        ProviderFail() {
            super("Fail", "1.0", "Fail");
            put("KeyPairGenerator.FOO" , "Failover$KeyPairGeneratorFail");
            put("KeyPairGenerator.DSA" , "Failover$KeyPairGeneratorFail");
            put("KeyPairGenerator.RSA" , "Failover$KeyPairGeneratorFail");
        }
    }

    public static class KeyPairGeneratorPass extends KeyPairGeneratorSpi {

        public void initialize(int keysize, SecureRandom random) {
            System.out.println("KeyPairGeneratorPass.initialize(" + keysize + ", " + random + ")");
        }

        public void initialize(AlgorithmParameterSpec params,
                SecureRandom random) throws InvalidAlgorithmParameterException {
            System.out.println("KeyPairGeneratorPass.initialize()");
        }

        public KeyPair generateKeyPair() {
            System.out.println("KeyPairGeneratorPass.generateKeyPair()");
            return null;
        }

    }

    public static class KeyPairGeneratorFail extends KeyPairGeneratorSpi {

        public void initialize(int keysize, SecureRandom random) {
            if (keysize != 512) {
                System.out.println("KeyPairGeneratorFail.initialize()");
                throw new InvalidParameterException();
            }
            System.out.println("KeyPairGeneratorFail.initialize() PASS");
        }

        public void initialize(AlgorithmParameterSpec params,
                SecureRandom random) throws InvalidAlgorithmParameterException {
            System.out.println("KeyPairGeneratorFail.initialize()");
            throw new InvalidParameterException();
        }

        public KeyPair generateKeyPair() {
            System.out.println("KeyPairGeneratorFail.generateKeyPair()");
            throw new InvalidParameterException();
        }

    }

}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.security.*;
import javax.crypto.KeyAgreement;

public abstract class KeyAgreementBench extends CryptoBase {

    @Param({})
    private String kpgAlgorithm;

    @Param({})
    private String algorithm;

    @Param({})
    private int keyLength;


    private KeyAgreement keyAgreement;
    private PrivateKey privKey;
    private PublicKey pubKey;

    @Setup
    public void setup() throws NoSuchAlgorithmException {
        setupProvider();
        KeyPairGenerator generator = (prov == null) ?
            KeyPairGenerator.getInstance(kpgAlgorithm) :
            KeyPairGenerator.getInstance(kpgAlgorithm, prov);
        generator.initialize(keyLength);
        KeyPair kpA = generator.generateKeyPair();
        privKey = kpA.getPrivate();
        KeyPair kpB = generator.generateKeyPair();
        pubKey = kpB.getPublic();

        keyAgreement = (prov == null) ?
            KeyAgreement.getInstance(algorithm) :
            KeyAgreement.getInstance(algorithm, prov);
    }

    @Benchmark
    public byte[] generateSecret() throws InvalidKeyException {
        keyAgreement.init(privKey);
        keyAgreement.doPhase(pubKey, true);
        return keyAgreement.generateSecret();
    }

    public static class DiffieHellman extends KeyAgreementBench {

        @Param({"DiffieHellman"})
        private String kpgAlgorithm;

        @Param({"DiffieHellman"})
        private String algorithm;

        @Param({"2048"})
        private int keyLength;

    }

    public static class EC extends KeyAgreementBench {

        @Param({"EC"})
        private String kpgAlgorithm;

        @Param({"ECDH"})
        private String algorithm;

        @Param({"256", "384", "521"})
        private int keyLength;

    }

    public static class XDH extends KeyAgreementBench {

        @Param({"XDH"})
        private String kpgAlgorithm;

        @Param({"XDH"})
        private String algorithm;

        @Param({"255", "448"})
        private int keyLength;

    }

}


/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package org.test.dummy;

import java.security.*;

public class DummyProvider extends Provider {
    public DummyProvider() {
        super("Dummy", "0.1", "Dummy Provider with nothing");
    }

    @Override
    public Provider configure(String configArg) {
        return new DummyProvider(configArg);
    }

    private DummyProvider(String arg) {
        super("Dummy", "0.2", "Dummy Provider with " + arg);
        //
        // KeyStore
        //
        put("KeyStore.DummyKS", "sun.security.provider.JavaKeyStore$JKS");

        //
        // Signature engines
        //
        put("Signature.SHA1withDSA",
            "sun.security.provider.DSA$SHA1withDSA");
        put("Alg.Alias.Signature.DSA", "SHA1withDSA");

        //
        // Key Pair Generator engines
        //
        put("KeyPairGenerator.DSA",
            "sun.security.provider.DSAKeyPairGenerator");

        //
        // Digest engines
        //
        put("MessageDigest.SHA", "sun.security.provider.SHA");
        put("Alg.Alias.MessageDigest.SHA1", "SHA");

        //
        // Algorithm Parameter Generator engines
        //
        put("AlgorithmParameterGenerator.DSA",
            "sun.security.provider.DSAParameterGenerator");

        //
        // Algorithm Parameter engines
        //
        put("AlgorithmParameters.DSA",
            "sun.security.provider.DSAParameters");

        //
        // Key factories
        //
        put("KeyFactory.DSA", "sun.security.provider.DSAKeyFactory");

        //
        // Certificate factories
        //
        put("CertificateFactory.X.509",
            "sun.security.provider.X509Factory");
        put("Alg.Alias.CertificateFactory.X509", "X.509");
    }
}

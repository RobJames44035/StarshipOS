/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.test.lib.security.timestamp;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Objects;

/**
 * A carrier for the TSA signer private key and certificate chain.
 */
public class SignerEntry {

    public final PrivateKey privateKey;
    public final X509Certificate[] certChain;
    public final X509Certificate cert;

    public SignerEntry(PrivateKey privateKey, X509Certificate[] certChain) {
        Objects.requireNonNull(privateKey);
        Objects.requireNonNull(certChain);

        this.privateKey = privateKey;
        this.certChain = certChain;
        this.cert = certChain[0];
    }
}

/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.crypto.provider;

import java.math.*;
import java.io.*;
import java.util.Arrays;

import sun.security.x509.AlgorithmId;
import sun.security.util.*;


/**
 * This class implements the <code>PrivateKeyInfo</code> type,
 * which is defined in PKCS #8 as follows:
 *
 * <pre>
 * PrivateKeyInfo ::=  SEQUENCE {
 *     version   INTEGER,
 *     privateKeyAlgorithm   AlgorithmIdentifier,
 *     privateKey   OCTET STRING,
 *     attributes   [0] IMPLICIT Attributes OPTIONAL }
 * </pre>
 *
 * @author Jan Luehe
 */
final class PrivateKeyInfo {

    // the version number defined by the PKCS #8 standard
    private static final BigInteger VERSION = BigInteger.ZERO;

    // the private-key algorithm
    private final AlgorithmId algid;

    // the private-key value
    private final byte[] privkey;

    /**
     * Constructs a PKCS#8 PrivateKeyInfo from its ASN.1 encoding.
     */
    PrivateKeyInfo(byte[] encoded) throws IOException {
        DerValue val = new DerValue(encoded);

        try {
            if (val.tag != DerValue.tag_Sequence)
                throw new IOException("private key parse error: not a sequence");

            // version
            BigInteger parsedVersion = val.data.getBigInteger();
            if (!parsedVersion.equals(VERSION)) {
                throw new IOException("version mismatch: (supported: " +
                        VERSION + ", parsed: " + parsedVersion);
            }

            // privateKeyAlgorithm
            this.algid = AlgorithmId.parse(val.data.getDerValue());

            // privateKey
            this.privkey = val.data.getOctetString();

            // OPTIONAL attributes not supported yet
        } finally {
            val.clear();
        }
    }

    /**
     * Returns the private-key algorithm.
     */
    AlgorithmId getAlgorithm() {
        return this.algid;
    }

    public void clear() {
        Arrays.fill(privkey, (byte)0);
    }
}

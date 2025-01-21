/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.crypto.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class generates a secret key for use with the HMAC-SHA1 algorithm.
 *
 * @author Jan Luehe
 *
 */

public final class HmacSHA1KeyGenerator extends KeyGeneratorSpi {

    private SecureRandom random = null;
    private int keysize = 64; // default keysize (in number of bytes)

    /**
     * Empty constructor
     */
    public HmacSHA1KeyGenerator() {
    }

    /**
     * Initializes this key generator.
     *
     * @param random the source of randomness for this generator
     */
    protected void engineInit(SecureRandom random) {
        this.random = random;
    }

    /**
     * Initializes this key generator with the specified parameter
     * set and a user-provided source of randomness.
     *
     * @param params the key generation parameters
     * @param random the source of randomness for this key generator
     *
     * @exception InvalidAlgorithmParameterException if <code>params</code> is
     * inappropriate for this key generator
     */
    protected void engineInit(AlgorithmParameterSpec params,
                              SecureRandom random)
        throws InvalidAlgorithmParameterException
    {
        throw new InvalidAlgorithmParameterException
            ("HMAC-SHA1 key generation does not take any parameters");
    }

    /**
     * Initializes this key generator for a certain keysize, using the given
     * source of randomness.
     *
     * @param keysize the keysize. This is an algorithm-specific
     * metric specified in number of bits.
     * @param random the source of randomness for this key generator
     */
    protected void engineInit(int keysize, SecureRandom random) {

        if (keysize <= 0) {
            throw new IllegalArgumentException("keysize must not be <= 0");
        }

        this.keysize = (keysize+7) / 8;
        this.engineInit(random);
    }

    /**
     * Generates an HMAC-SHA1 key.
     *
     * @return the new HMAC-SHA1 key
     */
    protected SecretKey engineGenerateKey() {
        if (this.random == null) {
            this.random = SunJCE.getRandom();
        }

        byte[] keyBytes = new byte[this.keysize];
        this.random.nextBytes(keyBytes);
        try {
            return new SecretKeySpec(keyBytes, "HmacSHA1");
        } finally {
            Arrays.fill(keyBytes, (byte)0);
        }
    }
}

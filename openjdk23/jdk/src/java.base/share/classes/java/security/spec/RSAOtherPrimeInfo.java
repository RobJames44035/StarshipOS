/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package java.security.spec;

import java.math.BigInteger;

/**
 * This class represents the triplet (prime, exponent, and coefficient)
 * inside RSA's OtherPrimeInfo structure, as defined in the
 * <a href="https://tools.ietf.org/rfc/rfc8017.txt">PKCS#1 v2.2</a> standard.
 * The ASN.1 syntax of RSA's OtherPrimeInfo is as follows:
 *
 * <pre>
 * OtherPrimeInfo ::= SEQUENCE {
 *   prime        INTEGER,
 *   exponent     INTEGER,
 *   coefficient  INTEGER
 * }
 *
 * </pre>
 *
 * @spec https://www.rfc-editor.org/info/rfc8017
 *      RFC 8017: PKCS #1: RSA Cryptography Specifications Version 2.2
 * @author Valerie Peng
 *
 *
 * @see RSAPrivateCrtKeySpec
 * @see java.security.interfaces.RSAMultiPrimePrivateCrtKey
 *
 * @since 1.4
 */

public class RSAOtherPrimeInfo {

    private final BigInteger prime;
    private final BigInteger primeExponent;
    private final BigInteger crtCoefficient;


   /**
    * Creates a new {@code RSAOtherPrimeInfo}
    * given the prime, primeExponent, and
    * crtCoefficient as defined in PKCS#1.
    *
    * @param prime the prime factor of n.
    * @param primeExponent the exponent.
    * @param crtCoefficient the Chinese Remainder Theorem
    * coefficient.
    * @throws    NullPointerException if any of the parameters, i.e.
    * {@code prime}, {@code primeExponent},
    * {@code crtCoefficient}, is null.
    *
    */
    public RSAOtherPrimeInfo(BigInteger prime,
                          BigInteger primeExponent,
                          BigInteger crtCoefficient) {
        if (prime == null) {
            throw new NullPointerException("the prime parameter must be " +
                                            "non-null");
        }
        if (primeExponent == null) {
            throw new NullPointerException("the primeExponent parameter " +
                                            "must be non-null");
        }
        if (crtCoefficient == null) {
            throw new NullPointerException("the crtCoefficient parameter " +
                                            "must be non-null");
        }
        this.prime = prime;
        this.primeExponent = primeExponent;
        this.crtCoefficient = crtCoefficient;
    }

    /**
     * Returns the prime.
     *
     * @return the prime.
     */
    public final BigInteger getPrime() {
        return this.prime;
    }

    /**
     * Returns the prime's exponent.
     *
     * @return the primeExponent.
     */
    public final BigInteger getExponent() {
        return this.primeExponent;
    }

    /**
     * Returns the prime's crtCoefficient.
     *
     * @return the crtCoefficient.
     */
    public final BigInteger getCrtCoefficient() {
        return this.crtCoefficient;
    }
}

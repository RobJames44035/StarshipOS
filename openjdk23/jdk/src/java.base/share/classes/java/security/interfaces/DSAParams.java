/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package java.security.interfaces;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Interface to a DSA-specific set of key parameters, which defines a
 * DSA <em>key family</em>. DSA (Digital Signature Algorithm) is defined
 * in NIST's FIPS-186.
 *
 * @see DSAKey
 * @see java.security.Key
 * @see java.security.Signature
 *
 * @author Benjamin Renaud
 * @author Josh Bloch
 * @since 1.1
 */
public interface DSAParams extends AlgorithmParameterSpec {

    /**
     * Returns the prime, {@code p}.
     *
     * @return the prime, {@code p}.
     */
    BigInteger getP();

    /**
     * Returns the subprime, {@code q}.
     *
     * @return the subprime, {@code q}.
     */
    BigInteger getQ();

    /**
     * Returns the base, {@code g}.
     *
     * @return the base, {@code g}.
     */
    BigInteger getG();
}

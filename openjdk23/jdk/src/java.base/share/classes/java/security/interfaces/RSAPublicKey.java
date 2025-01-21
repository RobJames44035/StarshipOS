/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package java.security.interfaces;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/**
 * The interface to an RSA public key.
 *
 * @author Jan Luehe
 * @since 1.2
 *
 */

public interface RSAPublicKey extends java.security.PublicKey, RSAKey
{
    /**
     * The type fingerprint that is set to indicate
     * serialization compatibility with a previous
     * version of the type.
     *
     * @deprecated A {@code serialVersionUID} field in an interface is
     * ineffectual. Do not use; no replacement.
     */
    @Deprecated
    @java.io.Serial
    long serialVersionUID = -8727434096241101194L;

    /**
     * Returns the public exponent.
     *
     * @return the public exponent
     */
    BigInteger getPublicExponent();

    /**
     * {@inheritDoc java.security.AsymmetricKey}
     *
     * @implSpec
     * The default implementation returns {@code null}.
     *
     * @return {@inheritDoc java.security.AsymmetricKey}
     * @since 11
     */
    @Override
    default AlgorithmParameterSpec getParams() {
        return null;
    }
}

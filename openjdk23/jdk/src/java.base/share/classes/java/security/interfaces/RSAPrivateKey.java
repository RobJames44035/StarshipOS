/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package java.security.interfaces;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/**
 * The interface to an RSA private key.
 *
 * @author Jan Luehe
 * @since 1.2
 *
 *
 * @see RSAPrivateCrtKey
 */

public interface RSAPrivateKey extends java.security.PrivateKey, RSAKey
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
    long serialVersionUID = 5187144804936595022L;

    /**
     * Returns the private exponent.
     *
     * @return the private exponent
     */
    BigInteger getPrivateExponent();

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

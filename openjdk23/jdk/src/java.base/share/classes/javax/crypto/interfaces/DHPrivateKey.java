/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package javax.crypto.interfaces;

import javax.crypto.spec.DHParameterSpec;
import java.math.BigInteger;

/**
 * The interface to a Diffie-Hellman private key.
 *
 * @author Jan Luehe
 *
 * @see DHKey
 * @see DHPublicKey
 * @since 1.4
 */
public interface DHPrivateKey extends DHKey, java.security.PrivateKey {

    /**
     * The class fingerprint that is set to indicate serialization
     * compatibility since J2SE 1.4.
     *
     * @deprecated A {@code serialVersionUID} field in an interface is
     * ineffectual. Do not use; no replacement.
     */
    @Deprecated
    @java.io.Serial
    long serialVersionUID = 2211791113380396553L;

    /**
     * Returns the private value, <code>x</code>.
     *
     * @return the private value, <code>x</code>
     */
    BigInteger getX();

    /**
     * {@inheritDoc java.security.AsymmetricKey}
     *
     * @implSpec
     * The default implementation returns {@code null}.
     *
     * @return {@inheritDoc java.security.AsymmetricKey}
     */
    @Override
    default DHParameterSpec getParams() {
        return null;
    }
}

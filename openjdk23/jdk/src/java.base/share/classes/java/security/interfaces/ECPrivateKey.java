/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package java.security.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.spec.ECParameterSpec;

/**
 * The interface to an elliptic curve (EC) private key.
 *
 * @author Valerie Peng
 *
 *
 * @see PrivateKey
 * @see ECKey
 *
 * @since 1.5
 */
public interface ECPrivateKey extends PrivateKey, ECKey {
   /**
    * The class fingerprint that is set to indicate
    * serialization compatibility.
    *
    * @deprecated A {@code serialVersionUID} field in an interface is
    * ineffectual. Do not use; no replacement.
    */
    @Deprecated
    @java.io.Serial
   long serialVersionUID = -7896394956925609184L;

    /**
     * Returns the private value S.
     * @return the private value S.
     */
    BigInteger getS();

    /**
     * {@inheritDoc java.security.AsymmetricKey}
     *
     * @implSpec
     * The default implementation returns {@code null}.
     *
     * @return {@inheritDoc java.security.AsymmetricKey}
     */
    @Override
    default ECParameterSpec getParams() {
        return null;
    }
}

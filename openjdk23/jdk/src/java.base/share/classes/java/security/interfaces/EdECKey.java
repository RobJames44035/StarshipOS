/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package java.security.interfaces;

import java.security.spec.NamedParameterSpec;

/**
 * An interface for an elliptic curve public/private key as defined by
 * <a href="https://tools.ietf.org/html/rfc8032">RFC 8032: Edwards-Curve
 * Digital Signature Algorithm (EdDSA)</a>. These keys are distinct from the
 * keys represented by {@code ECKey}, and they are intended for use with
 * algorithms based on RFC 8032 such as the EdDSA {@code Signature} algorithm.
 * This interface allows access to the algorithm parameters associated with
 * the key.
 *
 * @spec https://www.rfc-editor.org/info/rfc8032
 *      RFC 8032: Edwards-Curve Digital Signature Algorithm (EdDSA)
 * @since 15
 */
public interface EdECKey {
    /**
     * Returns the algorithm parameters associated with the key.
     *
     * @return the associated algorithm parameters.
     */
    NamedParameterSpec getParams();
}

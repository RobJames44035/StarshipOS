/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;

interface SSLKeyDerivation {
    SecretKey deriveKey(String algorithm,
            AlgorithmParameterSpec params) throws IOException;
}

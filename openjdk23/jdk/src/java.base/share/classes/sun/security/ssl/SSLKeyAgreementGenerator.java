/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;

interface SSLKeyAgreementGenerator {
    SSLKeyDerivation createKeyDerivation(
            HandshakeContext context) throws IOException;
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLHandshakeException;

final class SSLBasicKeyDerivation implements SSLKeyDerivation {
    private final String hashAlg;
    private final SecretKey secret;
    private final byte[] hkdfInfo;

    SSLBasicKeyDerivation(SecretKey secret, String hashAlg,
            byte[] label, byte[] context, int length) {
        this.hashAlg = hashAlg.replace("-", "");
        this.secret = secret;
        this.hkdfInfo = createHkdfInfo(label, context, length);
    }

    @Override
    public SecretKey deriveKey(String algorithm,
            AlgorithmParameterSpec keySpec) throws IOException {
        try {
            HKDF hkdf = new HKDF(hashAlg);
            return hkdf.expand(secret, hkdfInfo,
                    ((SecretSizeSpec)keySpec).length, algorithm);
        } catch (GeneralSecurityException gse) {
            throw new SSLHandshakeException("Could not generate secret", gse);
        }
    }

    private static byte[] createHkdfInfo(
            byte[] label, byte[] context, int length) {
        byte[] info = new byte[4 + label.length + context.length];
        ByteBuffer m = ByteBuffer.wrap(info);
        try {
            Record.putInt16(m, length);
            Record.putBytes8(m, label);
            Record.putBytes8(m, context);
        } catch (IOException ioe) {
            // unlikely
        }
        return info;
    }

    static class SecretSizeSpec implements AlgorithmParameterSpec {
        final int length;

        SecretSizeSpec(int length) {
            this.length = length;
        }
    }
}

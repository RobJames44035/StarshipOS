/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8347289
 * @summary make sure DPS works when non-extractable PRK is provided
 * @library /test/lib /test/jdk/security/unsignedjce
 * @build java.base/javax.crypto.ProviderVerifier
 * @enablePreview
 * @run main/othervm HKDFDelayedPRK
 */

import jdk.test.lib.Asserts;

import javax.crypto.KDF;
import javax.crypto.KDFParameters;
import javax.crypto.KDFSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

public class HKDFDelayedPRK {
    public static void main(String[] args) throws Exception {
        // This is a fake non-extractable key
        var prk = new SecretKey() {
            @Override
            public String getAlgorithm() {
                return "PRK";
            }

            @Override
            public String getFormat() {
                return null;
            }

            @Override
            public byte[] getEncoded() {
                return null;
            }
        };

        Security.addProvider(new ProviderImpl());
        var kdf = KDF.getInstance("HKDF-SHA256");
        kdf.deriveData(HKDFParameterSpec.expandOnly(prk, null, 32));

        // Confirms our own omnipotent impl is selected
        Asserts.assertEquals("P", kdf.getProviderName());
    }

    public static class ProviderImpl extends Provider {
        public ProviderImpl() {
            super("P", "1", "info");
            put("KDF.HKDF-SHA256", KDFImpl.class.getName());
        }
    }

    // This HKDF impl accepts everything
    public static class KDFImpl extends KDFSpi {

        public KDFImpl(KDFParameters params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        @Override
        protected KDFParameters engineGetParameters() {
            return null;
        }

        @Override
        protected SecretKey engineDeriveKey(String alg, AlgorithmParameterSpec dummy) {
            return new SecretKeySpec(new byte[32], alg);
        }

        @Override
        protected byte[] engineDeriveData(AlgorithmParameterSpec dummy) {
            return new byte[32];
        }
    }
}

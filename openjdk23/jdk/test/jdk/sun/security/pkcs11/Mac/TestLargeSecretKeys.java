/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import javax.crypto.*;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.Provider;

/*
 * @test
 * @bug 8328556
 * @library /test/lib ..
 * @run main/othervm/timeout=30 -DCUSTOM_P11_CONFIG_NAME=p11-nss-sensitive.txt TestLargeSecretKeys
 */

public final class TestLargeSecretKeys extends PKCS11Test {

    public void main(Provider p) throws Exception {
        Key k = generateLargeSecretKey(p);
        Mac m = Mac.getInstance("HmacSHA512", p);
        m.init(k);
        m.doFinal(new byte[10]);
        // Before the fix for 8328556, the next call would require to re-build
        // the key in the NSS Software Token by means of a call to
        // C_UnwrapKey because the key's CKA_SENSITIVE attribute is CK_TRUE.
        // This call would fail with a CKR_TEMPLATE_INCONSISTENT error due to
        // secret key length checks in NSS: length of 384 bytes is greater
        // than 256 (defined as MAX_KEY_LEN in pkcs11i.h). With 8328556, the
        // key was never extracted after its first use so re-building for the
        // second use is not necessary.
        m.init(k);
        m.doFinal(new byte[10]);
    }

    private static Key generateLargeSecretKey(Provider p) throws Exception {
        KeyPairGenerator kpg1 = KeyPairGenerator.getInstance("DH", p);
        KeyPairGenerator kpg2 = KeyPairGenerator.getInstance("DH", p);
        kpg1.initialize(3072);
        kpg2.initialize(3072);
        Key privK = kpg1.generateKeyPair().getPrivate();
        Key pubK = kpg2.generateKeyPair().getPublic();
        KeyAgreement ka = KeyAgreement.getInstance("DH", p);
        ka.init(privK);
        ka.doPhase(pubK, true);
        return ka.generateSecret("TlsPremasterSecret");
    }

    public static void main(String[] args) throws Exception {
        main(new TestLargeSecretKeys());
    }
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.security.CertUtils;

import java.security.KeyStore;
import java.security.SecureRandom;

/*
 * @test
 * @bug 6415696 6931562 8180570
 * @requires os.family == "windows"
 * @library /test/lib
 * @summary Test "keytool -changealias" using the Microsoft CryptoAPI provider.
 */
public class KeytoolChangeAlias {
    public static void main(String[] args) throws Exception {
        SecureRandom random = new SecureRandom();
        String alias = Integer.toString(random.nextInt(1000, 8192));
        String newAlias = alias + "1";
        KeyStore ks = KeyStore.getInstance("Windows-MY");
        ks.load(null, null);

        try {
            ks.setCertificateEntry(alias, CertUtils.getCertFromFile("246810.cer"));

            if (ks.containsAlias(newAlias)) {
                ks.deleteEntry(newAlias);
            }

            int before = ks.size();

            ks.store(null, null); // no-op, but let's do it before a keytool command

            SecurityTools.keytool("-changealias",
                    "-storetype", "Windows-My",
                    "-alias", alias,
                    "-destalias", newAlias).shouldHaveExitValue(0);

            ks.load(null, null);

            if (ks.size() != before) {
                throw new Exception("error: unexpected number of entries in the "
                        + "Windows-MY store. Before: " + before
                        + ". After: " + ks.size());
            }

            if (!ks.containsAlias(newAlias)) {
                throw new Exception("error: cannot find the new alias name"
                        + " in the Windows-MY store");
            }
        } finally {
            try {
                ks.deleteEntry(newAlias);
            } catch (Exception e) {
                System.err.println("Couldn't delete alias " + newAlias);
                e.printStackTrace(System.err);
            }
            try {
                ks.deleteEntry(alias);
            } catch (Exception e) {
                System.err.println("Couldn't delete alias " + alias);
                e.printStackTrace(System.err);
            }
            ks.store(null, null);
        }
    }
}

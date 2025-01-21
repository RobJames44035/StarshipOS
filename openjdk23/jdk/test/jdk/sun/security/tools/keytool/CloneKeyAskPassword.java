/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 6178366
 * @library /test/lib
 * @summary confirm that keytool correctly finds (and clones) a private key
 *          when the user is prompted for the key's password.
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;

public class CloneKeyAskPassword {
    public static void main(String[] args) throws Exception {

        // Different storepass and keypass
        Files.copy(Path.of(
                    System.getProperty("test.src"), "CloneKeyAskPassword.jks"),
                Path.of("CloneKeyAskPassword.jks"));

        // Clone with original keypass
        SecurityTools.setResponse("test456", "");
        SecurityTools.keytool(
                "-keyclone",
                "-alias", "mykey",
                "-dest", "myclone1",
                "-keystore", "CloneKeyAskPassword.jks",
                "-storepass", "test123").shouldHaveExitValue(0);

        // Clone with new keypass
        SecurityTools.setResponse("test456", "test789", "test789");
        SecurityTools.keytool(
                "-keyclone",
                "-alias", "mykey",
                "-dest", "myclone2",
                "-keystore", "CloneKeyAskPassword.jks",
                "-storepass", "test123").shouldHaveExitValue(0);

        KeyStore ks = KeyStore.getInstance(
                new File("CloneKeyAskPassword.jks"), "test123".toCharArray());
        Asserts.assertNotNull(ks.getKey("myclone1", "test456".toCharArray()));
        Asserts.assertNotNull(ks.getKey("myclone2", "test789".toCharArray()));
    }
}

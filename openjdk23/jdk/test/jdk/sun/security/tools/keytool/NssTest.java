/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * @test
 * @summary It tests (almost) all keytool behaviors with NSS.
 * @library /test/lib /test/jdk/sun/security/pkcs11 /java/security/testlibrary
 * @modules java.base/sun.security.tools.keytool
 *          java.base/sun.security.util
 *          java.base/sun.security.x509
 * @run main/othervm/timeout=600 NssTest
 */
public class NssTest {

    public static void main(String[] args) throws Exception {
        Path libPath = PKCS11Test.getNSSLibPath("softokn3");
        if (libPath == null) {
            return;
        }
        System.out.println("Using NSS lib at " + libPath);

        copyFiles();
        System.setProperty("nss", "");
        System.setProperty("nss.lib", String.valueOf(libPath));

        PKCS11Test.loadNSPR(libPath.getParent().toString());
        KeyToolTest.main(args);
    }

    private static void copyFiles() throws IOException {
        Path srcPath = Paths.get(System.getProperty("test.src"));
        Files.copy(srcPath.resolve("p11-nss.txt"), Paths.get("p11-nss.txt"));

        Path dbPath = srcPath.getParent().getParent()
                .resolve("pkcs11").resolve("nss").resolve("db");
        Path destDir = Path.of( "tmpdb");
        Files.createDirectory(destDir);
        Files.copy(dbPath.resolve("cert9.db"), destDir.resolve("cert9.db"));
        Files.copy(dbPath.resolve("key4.db"), destDir.resolve("key4.db"));
    }
}

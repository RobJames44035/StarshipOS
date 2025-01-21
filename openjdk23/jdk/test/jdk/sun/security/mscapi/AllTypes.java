/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/*
 * @test
 * @bug 6782021
 * @requires os.family == "windows"
 * @library /test/lib
 * @summary More keystore types
 */
public class AllTypes {

    public static void main(String[] args) throws Exception {
        var nm = test("windows-my");
        var nr = test("windows-root");
        var nmu = test("windows-my-currentuser");
        var nru = test("windows-root-currentuser");
        var nmm = test("windows-my-localmachine");
        var nrm = test("windows-root-localmachine");
        Asserts.assertEQ(nm, nmu);
        Asserts.assertEQ(nr, nru);
    }

    private static List<String> test(String type) throws Exception {
        var stdType = "Windows-" + type.substring(8).toUpperCase(Locale.ROOT);
        SecurityTools.keytool("-storetype " + type + " -list")
        .shouldHaveExitValue(0)
        .shouldContain("Keystore provider: SunMSCAPI")
        .shouldContain("Keystore type: " + stdType);
        KeyStore ks = KeyStore.getInstance(type);
        ks.load(null, null);
        var content = Collections.list(ks.aliases());
        Collections.sort(content);
        return content;
    }
}
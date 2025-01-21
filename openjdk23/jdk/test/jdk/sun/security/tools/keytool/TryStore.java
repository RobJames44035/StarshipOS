/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7047200
 * @summary keytool can try save to a byte array before overwrite the file
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TryStore {
    public static void main(String[] args) throws Exception {
        keytool("-genkeypair -alias a -dname CN=A -storepass changeit -keypass changeit");
        keytool("-genkeypair -alias b -dname CN=B -storepass changeit -keypass changeit");

        // We use -protected for JKS keystore. This is illegal so the command should
        // fail. Then we can check if the keystore is damaged.

        keytool("-genkeypair -protected -alias b -delete -debug")
                .shouldNotHaveExitValue(0);

        keytool("-list -storepass changeit")
                .shouldHaveExitValue(0);
    }

    static OutputAnalyzer keytool(String s) throws Exception {
        return SecurityTools.keytool(
                "-storetype jks -keystore trystore.jks -keyalg rsa " + s);
    }
}

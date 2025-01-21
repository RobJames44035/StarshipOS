/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8231598
 * @requires os.family == "windows"
 * @library /test/lib
 * @summary keytool does not export sun.security.mscapi
 */

import jdk.test.lib.SecurityTools;

public class ProviderClassOption {
    public static void main(String[] args) throws Throwable {
        SecurityTools.keytool("-v -storetype Windows-ROOT -list"
                + " -providerClass sun.security.mscapi.SunMSCAPI")
            .shouldHaveExitValue(0);
    }
}

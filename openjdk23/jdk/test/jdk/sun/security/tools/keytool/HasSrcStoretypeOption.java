/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8162752
 * @summary keytool -importkeystore should probe srcstoretype if not specified
 * @modules java.base/sun.security.tools.keytool
 */

import sun.security.tools.keytool.Main;

public class HasSrcStoretypeOption {

    public static void main(String[] args) throws Exception {
        run("-genkeypair -keyalg DSA -alias a -dname CN=A -storetype jceks -keystore jce");
        // When there is no -srcstoretype, it should be probed from the file
        run("-importkeystore -srckeystore jce -destkeystore jks -deststoretype jks");
    }

    private static void run(String cmd) throws Exception {
        cmd += " -debug -storepass changeit -keypass changeit -srcstorepass changeit";
        Main.main(cmd.split(" "));
    }
}

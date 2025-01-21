/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6489721
 * @summary keytool has not closed several file streams
 * @author weijun.wang
 * @modules java.base/sun.security.tools.keytool
 * @compile -XDignore.symbol.file CloseFile.java
 * @run main CloseFile
 *
 * This test is only useful on Windows, which fails before the fix and succeeds
 * after it. On other platforms, it always passes.
 */

import java.io.*;

public class CloseFile {
    public static void main(String[] args) throws Exception {
        remove("f0", false);
        remove("f1", false);
        run("-keystore f0 -storepass changeit -keypass changeit -genkeypair -dname CN=Haha");
        run("-keystore f0 -storepass changeit -keypass changeit -certreq -file f2");
        remove("f2", true);
        run("-keystore f0 -storepass changeit -keypass changeit -exportcert -file f2");
        remove("f2", true);
        run("-keystore f0 -storepass changeit -keypass changeit -exportcert -file f2");
        run("-keystore f0 -storepass changeit -keypass changeit -delete -alias mykey");
        run("-keystore f0 -storepass changeit -keypass changeit -importcert -file f2 -noprompt");
        remove("f2", true);
        run("-keystore f0 -storepass changeit -keypass changeit -exportcert -file f2");
        run("-printcert -file f2");
        remove("f2", true);
        remove("f0", true);
        run("-keystore f0 -storepass changeit -keypass changeit -genkeypair -dname CN=Haha");
        run("-importkeystore -srckeystore f0 -destkeystore f1 -srcstorepass changeit -deststorepass changeit");
        remove("f0", true);
        remove("f1", true);
    }

    static void run(String s) throws Exception {
        sun.security.tools.keytool.Main.main((s+" -debug -keyalg rsa").split(" "));
    }
    static void remove(String filename, boolean check) {
        new File(filename).delete();
        if (check && new File(filename).exists()) {
            throw new RuntimeException("Error deleting " + filename);
        }
    }
}

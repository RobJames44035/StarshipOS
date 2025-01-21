/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8162739
 * @summary Create new keytool option to access cacerts file
 * @modules java.base/sun.security.tools.keytool
 *          java.base/sun.security.tools
 * @run main/othervm -Duser.language=en -Duser.country=US CacertsOption
 */

import sun.security.tools.KeyStoreUtil;
import sun.security.tools.keytool.Main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.security.KeyStore;
import java.util.Collections;

public class CacertsOption {

    public static void main(String[] args) throws Exception {

        run("-help -list");
        if (!msg.contains("-cacerts")) {
            throw new Exception("No cacerts in help:\n" + msg);
        }

        String cacerts = KeyStoreUtil.getCacerts();

        run("-list -keystore " + cacerts);
        if (!msg.contains("Warning:")) {
            throw new Exception("No warning in output:\n" + msg);
        }

        run("-list -cacerts");
        KeyStore ks = KeyStore.getInstance(new File(cacerts), (char[])null);
        for (String alias: Collections.list(ks.aliases())) {
            if (!msg.contains(alias)) {
                throw new Exception(alias + " not found in\n" + msg);
            }
        }

        try {
            run("-list -cacerts -storetype jks");
            throw new Exception("Should fail");
        } catch (IllegalArgumentException iae) {
            if (!msg.contains("cannot be used with")) {
                throw new Exception("Bad error msg\n" + msg);
            }
        }
    }

    private static String msg = null;

    private static void run(String cmd) throws Exception {
        msg = null;
        cmd += " -storepass changeit -debug";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bout);
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        try {
            System.setOut(ps);
            System.setErr(ps);
            Main.main(cmd.split(" "));
        } finally {
            System.setErr(oldErr);
            System.setOut(oldOut);
            msg = new String(bout.toByteArray());
        }
    }
}

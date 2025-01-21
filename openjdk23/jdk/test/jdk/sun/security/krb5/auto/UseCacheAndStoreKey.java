/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7201053 8194486
 * @summary Krb5LoginModule shows NPE when both useTicketCache and storeKey
 *          are set to true
 * @library /test/lib
 * @compile -XDignore.symbol.file UseCacheAndStoreKey.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts UseCacheAndStoreKey
 */

import java.io.FileOutputStream;
import javax.security.auth.login.LoginException;

// The basic krb5 test skeleton you can copy from
public class UseCacheAndStoreKey {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).writeJAASConf();

        // KDC would save ccache for client
        System.setProperty("test.kdc.save.ccache", "cache.here");
        try (FileOutputStream fos = new FileOutputStream(OneKDC.JAAS_CONF)) {
            fos.write((
                "me {\n" +
                "    com.sun.security.auth.module.Krb5LoginModule required\n" +
                "    principal=\"" + OneKDC.USER + "\"\n" +
                "    useTicketCache=true\n" +
                "    ticketCache=cache.here\n" +
                "    isInitiator=true\n" +
                "    storeKey=true;\n};\n"
                ).getBytes());
        }

        // The first login will use default callback and succeed
        Context.fromJAAS("me");

        // The second login uses ccache and won't be able to store the keys
        try {
            Context.fromJAAS("me");
            throw new Exception("Should fail");
        } catch (LoginException le) {
            if (le.getMessage().indexOf("NullPointerException") >= 0
                    || le.getCause() instanceof NullPointerException) {
                throw new Exception("NPE");
            }
        }
    }
}

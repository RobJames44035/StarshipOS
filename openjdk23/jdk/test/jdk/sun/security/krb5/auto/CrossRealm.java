/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6706974 8194486
 * @summary Add krb5 test infrastructure
 * @library /test/lib
 * @compile -XDignore.symbol.file CrossRealm.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts CrossRealm
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Security;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import sun.security.jgss.GSSUtil;

public class CrossRealm implements CallbackHandler {
    public static void main(String[] args) throws Exception {
        startKDCs();
        xRealmAuth();
    }

    static void startKDCs() throws Exception {
        // Create and start the KDC
        KDC kdc1 = KDC.create("RABBIT.HOLE");
        kdc1.addPrincipal("dummy", "bogus".toCharArray());
        kdc1.addPrincipalRandKey("krbtgt/RABBIT.HOLE");
        kdc1.addPrincipal("krbtgt/SNAKE.HOLE@RABBIT.HOLE",
                "rabbit->snake".toCharArray());

        KDC kdc2 = KDC.create("SNAKE.HOLE");
        kdc2.addPrincipalRandKey("krbtgt/SNAKE.HOLE");
        kdc2.addPrincipal("krbtgt/SNAKE.HOLE@RABBIT.HOLE",
                "rabbit->snake".toCharArray());
        kdc2.addPrincipalRandKey("host/www.snake.hole");

        KDC.saveConfig("krb5-localkdc.conf", kdc1, kdc2,
                "forwardable=true",
                "[domain_realm]",
                ".snake.hole=SNAKE.HOLE");
        System.setProperty("java.security.krb5.conf", "krb5-localkdc.conf");
    }

    static void xRealmAuth() throws Exception {
        Security.setProperty("auth.login.defaultCallbackHandler", "CrossRealm");
        System.setProperty("java.security.auth.login.config", "jaas-localkdc.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        FileOutputStream fos = new FileOutputStream("jaas-localkdc.conf");
        fos.write(("com.sun.security.jgss.krb5.initiate {\n" +
                "    com.sun.security.auth.module.Krb5LoginModule\n" +
                "    required\n" +
                "    principal=dummy\n" +
                "    doNotPrompt=false\n" +
                "    useTicketCache=false\n" +
                "    ;\n" +
                "};").getBytes());
        fos.close();

        GSSManager m = GSSManager.getInstance();
        m.createContext(
                m.createName("host@www.snake.hole", GSSName.NT_HOSTBASED_SERVICE),
                GSSUtil.GSS_KRB5_MECH_OID,
                null,
                GSSContext.DEFAULT_LIFETIME).initSecContext(new byte[0], 0, 0);
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName("dummy");
            }
            if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback).setPassword("bogus".toCharArray());
            }
        }
    }
}

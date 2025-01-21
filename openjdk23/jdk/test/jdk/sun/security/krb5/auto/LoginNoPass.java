/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8028351 8194486
 * @summary JWS doesn't get authenticated when using kerberos auth proxy
 * @library /test/lib
 * @compile -XDignore.symbol.file LoginNoPass.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts LoginNoPass
 */

import sun.security.jgss.GSSUtil;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.security.Security;

public class LoginNoPass {

    static boolean kdcTouched = false;
    public static void main(String[] args) throws Exception {

        new OneKDC(null) {
            protected byte[] processAsReq(byte[] in) throws Exception {
                kdcTouched = true;
                return super.processAsReq(in);
            }
        }.writeJAASConf();
        Security.setProperty("auth.login.defaultCallbackHandler",
                "LoginNoPass$CallbackForClient");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

        try {
            Context c;
            c = Context.fromJAAS("client");
            c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
            c.take(new byte[0]);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            // OK
        }
        if (kdcTouched) {
            throw new Exception("Failed");
        }
    }
    public static class CallbackForClient implements CallbackHandler {
        public void handle(Callback[] callbacks) {
            // Do nothing
        }
    }
}

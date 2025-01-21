/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4850423
 * @summary login facilities for hardware tokens
 * @library /test/lib ..
 * @run testng/othervm Login
 */

import jtreg.SkippedException;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Path;
import java.security.*;
import javax.security.auth.callback.*;

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;

public class Login extends PKCS11Test {

    private static final String KS_TYPE = "PKCS11";
    private static char[] password;

    @BeforeClass
    public void setUp() throws Exception {
        copyNssCertKeyToClassesDir();
        setCommonSystemProps();
        System.setProperty("CUSTOM_P11_CONFIG",
                Path.of(BASE).resolve("Login-nss.txt").toString());
    }

    @Test
    public void testLogin() throws Exception {
        try {
            main(new Login(), new String[0]);
        } catch (SkippedException se) {
            throw new SkipException("One or more tests are skipped");
        }
    }

    public void main(Provider p) throws Exception {

        int testnum = 1;

        KeyStore ks = KeyStore.getInstance(KS_TYPE, p);

        // check instance
        if (ks.getProvider() instanceof AuthProvider ap) {
            System.out.println("keystore provider instance of AuthProvider");
            System.out.println("test " + testnum++ + " passed");
        } else {
            throw new SecurityException("did not get AuthProvider KeyStore");
        }

        try {

            // test app-provided callback
            System.out.println("*** enter [foo] as the password ***");
            password = new char[] { 'f', 'o', 'o' };

            ap.login(new Subject(), new PasswordCallbackHandler());
            ap.logout();
            throw new SecurityException("test failed, expected LoginException");
        } catch (FailedLoginException fle) {
            System.out.println("test " + testnum++ + " passed");
        }

        try {

            // test default callback
            System.out.println("*** enter [foo] as the password ***");
            password = new char[] { 'f', 'o', 'o' };

            Security.setProperty("auth.login.defaultCallbackHandler",
                "Login$PasswordCallbackHandler");
            ap.login(new Subject(), null);
            ap.logout();
            throw new SecurityException("test failed, expected LoginException");
        } catch (FailedLoginException fle) {
            System.out.println("test " + testnum++ + " passed");
        }

        // test provider-set callback
        System.out.println("*** enter test12 (correct) password ***");
        password = new char[] { 't', 'e', 's', 't', '1', '2' };

        Security.setProperty("auth.login.defaultCallbackHandler", "");
        ap.setCallbackHandler(new PasswordCallbackHandler());
        ap.login(new Subject(), null);
        System.out.println("test " + testnum++ + " passed");

        // test user already logged in
        ap.setCallbackHandler(null);
        ap.login(new Subject(), null);
        System.out.println("test " + testnum++ + " passed");

        // logout
        ap.logout();

        // call KeyStore.load with a NULL password, and get prompted for PIN
        ap.setCallbackHandler(new PasswordCallbackHandler());
        ks.load(null, (char[])null);
        System.out.println("test " + testnum++ + " passed");
    }

    public static class PasswordCallbackHandler implements CallbackHandler {
        public void handle(Callback[] callbacks)
                throws IOException, UnsupportedCallbackException {
            if (!(callbacks[0] instanceof PasswordCallback pc)) {
                throw new UnsupportedCallbackException(callbacks[0]);
            }
            pc.setPassword(Login.password);
        }
    }
}

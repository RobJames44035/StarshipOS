/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
import java.lang.ref.Reference;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @test
 * @bug 8169068
 * @summary  Basic test for Authenticator.getDefault()
 * @run main/othervm GetAuthenticatorTest
 */
public class GetAuthenticatorTest {

    static final class MyAuthenticator extends Authenticator {

        MyAuthenticator () {
            super ();
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication () {
            System.out.println ("Auth called");
            return (new PasswordAuthentication ("user",
                        "passwordNotCheckedAnyway".toCharArray()));
        }

    }

    public static void main (String args[]) throws Exception {
        Authenticator defaultAuth = Authenticator.getDefault();
        if (defaultAuth != null) {
            throw new RuntimeException("Unexpected authenticator: null expected");
        }
        MyAuthenticator auth = new MyAuthenticator();
        Authenticator.setDefault(auth);
        defaultAuth = Authenticator.getDefault();
        if (defaultAuth != auth) {
            throw new RuntimeException("Unexpected authenticator: auth expected");
        }
        System.out.println("Test passed with default authenticator "
                           + defaultAuth);
    }
}

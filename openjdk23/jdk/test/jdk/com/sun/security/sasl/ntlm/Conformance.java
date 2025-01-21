/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7043847 7043860 7043882 7043938 7043959
 * @summary NTML impl of SaslServer conformance errors
 */
import java.io.IOException;
import javax.security.sasl.*;
import java.util.*;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class Conformance {

    public static void main(String[] args) throws Exception {
        try {
            Sasl.createSaslClient(new String[] {"NTLM"}, "abc", "ldap",
                    "server", new HashMap<String, Object>(), null);
        } catch (SaslException se) {
            System.out.println(se);
        }
        try {
            Sasl.createSaslServer("NTLM", "ldap",
                    "server", new HashMap<String, Object>(), null);
        } catch (SaslException se) {
            System.out.println(se);
        }
        try {
            Sasl.createSaslClient(new String[] {"NTLM"}, "abc", "ldap",
                    "server", null, new CallbackHandler() {
                        @Override
                        public void handle(Callback[] callbacks) throws
                                IOException, UnsupportedCallbackException {  }
                    });
        } catch (SaslException se) {
            System.out.println(se);
        }
        try {
            SaslServer saslServer =
                    Sasl.createSaslServer("NTLM", "ldap", "abc", null, new CallbackHandler() {
                        @Override
                        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {  }
                    });
            System.err.println("saslServer = " + saslServer);
            System.err.println("saslServer.isComplete() = " + saslServer.isComplete());
            // IllegalStateException is expected here
            saslServer.getNegotiatedProperty("prop");
            System.err.println("No IllegalStateException");
        } catch (IllegalStateException se) {
            System.out.println(se);
        }
        try {
            SaslServer saslServer =
                    Sasl.createSaslServer("NTLM", "ldap", "abc", null, new CallbackHandler() {
                        @Override
                        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {  }
                    });
            System.err.println("saslServer = " + saslServer);
            System.err.println("saslServer.isComplete() = " + saslServer.isComplete());
            // IllegalStateException is expected here
            saslServer.getAuthorizationID();
            System.err.println("No IllegalStateException");
        } catch (IllegalStateException se) {
            System.out.println(se);
        }
        try {
            SaslServer saslServer =
                    Sasl.createSaslServer("NTLM", "ldap", "abc", null, new CallbackHandler() {
                        @Override
                        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {  }
                    });
            System.err.println("saslServer = " + saslServer);
            System.err.println("saslServer.isComplete() = " + saslServer.isComplete());
            // IllegalStateException is expected here
            saslServer.wrap(new byte[0], 0, 0);
            System.err.println("No IllegalStateException");
        } catch (IllegalStateException se) {
            System.out.println(se);
        }
    }
}

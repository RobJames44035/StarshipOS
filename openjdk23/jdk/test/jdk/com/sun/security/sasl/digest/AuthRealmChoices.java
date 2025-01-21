/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8013855
 * @run main AuthRealmChoices 1
 * @run main AuthRealmChoices 2
 * @summary DigestMD5Client has not checked RealmChoiceCallback value
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.*;

public class AuthRealmChoices {
    private static final String MECH = "DIGEST-MD5";
    private static final String SERVER_FQDN = "machineX.imc.org";
    private static final String PROTOCOL = "jmx";

    private static final byte[] EMPTY = new byte[0];

    public static void main(String[] args) throws Exception {

        Map props = new HashMap();
        props.put("com.sun.security.sasl.digest.realm",
            "IMC.ORG foo.bar machineX");

        SaslClient clnt = Sasl.createSaslClient(
            new String[]{MECH}, null, PROTOCOL, SERVER_FQDN, null,
                new CallbackHandler() {
                    @Override
                    public void handle(Callback[] callbacks)
                            throws IOException, UnsupportedCallbackException {
                        for (Callback cb: callbacks) {
                            if (cb instanceof RealmChoiceCallback) {
                                // Two tests:
                                // 1. Set an index out of bound
                                // 2. No index set at all
                                if (args[0].equals("1")) {
                                    ((RealmChoiceCallback)cb).setSelectedIndex(10);
                                }
                            }
                        }
                    }
                });

        SaslServer srv = Sasl.createSaslServer(MECH, PROTOCOL, SERVER_FQDN, props,
            new CallbackHandler() {
                @Override
                public void handle(Callback[] callbacks)
                        throws IOException, UnsupportedCallbackException {
                    for (Callback cb: callbacks) {
                        System.out.println(cb);
                    }
                }
            });

        byte[] challenge = srv.evaluateResponse(EMPTY);

        try {
            clnt.evaluateChallenge(challenge);
            throw new Exception();
        } catch (SaslException se) {
            System.out.println(se);
        }
    }
}

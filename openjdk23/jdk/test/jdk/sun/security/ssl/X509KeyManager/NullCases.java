/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6302126 6302321 6302271 6302304
 * @summary KeyManagerFactory.init method throws unspecified exception
 *     for NewSunX509 algorithm
 *     X509KeyManager implementation for NewSunX509 throws unspecified
 *     ProviderException
 *     X509KeyManager implementation for NewSunX509 algorithm returns empty
 *     arrays instead of null
 *     X509KeyManager implementation for NewSunX509 throws unspecified
 *     NullPointerException
 */
import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;

import java.util.*;


public class NullCases {
    public static void main(String[] args) throws Exception {
        KeyManagerFactory kmf;
        X509KeyManager km;
        char [] password = {' '};

        // check for bug 6302126
        kmf = KeyManagerFactory.getInstance("NewSunX509");
        kmf.init((KeyStore)null, password);

        // check for 6302321
        km = (X509KeyManager) kmf.getKeyManagers()[0];
        X509Certificate[] certs = km.getCertificateChain("doesnotexist");
        PrivateKey priv = km.getPrivateKey("doesnotexist");
        if (certs != null || priv != null) {
            throw new Exception("Should return null if the alias can't be found");
        }

        // check for 6302271
        String[] clis = km.getClientAliases("doesnotexist", null);
        if (clis != null && clis.length == 0) {
            throw new Exception("Should return null instead of empty array");
        }
        String[] srvs = km.getServerAliases("doesnotexist", null);
        if (srvs != null && srvs.length == 0) {
            throw new Exception("Should return null instead of empty array");
        }

        // check for 6302304
        km.getServerAliases(null, null);
        km.getClientAliases(null, null);
        km.getCertificateChain(null);
        km.getPrivateKey(null);
        km.chooseServerAlias(null, null, null);
        km.chooseClientAlias(null, null, null);
    }
}

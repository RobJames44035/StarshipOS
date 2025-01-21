/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import javax.net.ssl.X509KeyManager;
import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;
import java.net.Socket;
import javax.net.ssl.X509KeyManager;
import java.util.Set;

final class MyKeyManager implements X509KeyManager {
    private HashMap keyMap = new HashMap();
    private HashMap certChainMap = new HashMap();

    MyKeyManager(KeyStore ks, char[] password)
        throws KeyStoreException, NoSuchAlgorithmException,
        UnrecoverableKeyException
    {
        if (ks == null) {
            return;
        }

        Enumeration aliases = ks.aliases();
        while (aliases.hasMoreElements()) {
            String alias = (String)aliases.nextElement();
            if (ks.isKeyEntry(alias)) {
                Certificate[] certs;
                certs = ks.getCertificateChain(alias);
                if (certs != null && certs.length > 0 &&
                    certs[0] instanceof X509Certificate) {
                    if (!(certs instanceof X509Certificate[])) {
                        Certificate[] tmp = new X509Certificate[certs.length];
                        System.arraycopy(certs, 0, tmp, 0, certs.length);
                        certs = tmp;
                    }
                    Key key = ks.getKey(alias, password);
                    certChainMap.put(alias, certs);
                    keyMap.put(alias, key);
                }
            }
        }
    }

    /*
     * Choose an alias to authenticate the client side of a secure
     * socket given the public key type and the list of
     * certificate issuer authorities recognized by the peer (if any).
     */
    public String chooseClientAlias(String[] keyTypes, Principal[] issuers,
            Socket socket) {
        return "client";
    }

    /*
     * Get the matching aliases for authenticating the client side of a secure
     * socket given the public key type and the list of
     * certificate issuer authorities recognized by the peer (if any).
     */
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        String[] s = new String[1];
        s[0] = "client";
        return s;
    }

    private HashMap serverAliasCache = new HashMap();

    /*
     * Choose an alias to authenticate the server side of a secure
     * socket given the public key type and the list of
     * certificate issuer authorities recognized by the peer (if any).
     */
    public synchronized String chooseServerAlias(String keyType,
            Principal[] issuers, Socket socket) {
        return "server";
    }

    /*
     * Get the matching aliases for authenticating the server side of a secure
     * socket given the public key type and the list of
     * certificate issuer authorities recognized by the peer (if any).
     */
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        String[] s = new String[1];
        s[0] = "server";
        return s;
    }

    /**
     * Returns the certificate chain associated with the given alias.
     *
     * @param alias the alias name
     *
     * @return the certificate chain (ordered with the user's certificate first
     * and the root certificate authority last)
     *
     * @exception KeyStoreException if the alias is invalid
     */
    public X509Certificate[] getCertificateChain(String alias) {
        Object chain;

        chain = certChainMap.get(alias);
        if (!(chain instanceof X509Certificate[]))
            return null;
        return (X509Certificate[]) chain;
    }

    /*
     * Returns the key associated with the given alias, using the given
     * password to recover it.
     *
     * @param alias the alias name
     *
     * @return the requested key
     * @exception KeyStoreException if the alias is invalid
     */
    public PrivateKey getPrivateKey(String alias) {
        Object key;

        key = keyMap.get(alias);
        if (!(key instanceof PrivateKey))
            return null;
        return (PrivateKey)key;
    }
}

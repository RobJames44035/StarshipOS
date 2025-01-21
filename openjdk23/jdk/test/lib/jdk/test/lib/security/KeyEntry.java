/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.test.lib.security;

/*
 * An entry in key store.
 */
public class KeyEntry {

    // The key algorithm
    public final String keyAlgo;

    // The PEM-encoded PKCS8 key string
    public final String keyStr;

    // The password to protect the key
    public final String password;

    // The certificate chain
    // Every certificate is a PEM-encoded string
    public final String[] certStrs;

    public KeyEntry(String keyAlgo, String keyStr, String password,
            String[] certStrs) {
        this.keyAlgo = keyAlgo;
        this.keyStr = keyStr;
        this.password = password;
        this.certStrs = certStrs;
    }

    public KeyEntry(String keyAlgo, String keyStr, String[] certStrs) {
        this(keyAlgo, keyStr, null, certStrs);
    }
}

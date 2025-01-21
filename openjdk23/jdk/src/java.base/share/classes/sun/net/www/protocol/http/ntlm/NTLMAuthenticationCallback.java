/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.net.www.protocol.http.ntlm;

import java.net.URL;

/**
 * This class is used to call back to deployment to determine if a given
 * URL is trusted. Transparent authentication (try with logged in users
 * credentials without prompting) should only be tried with trusted sites.
 */
public abstract class NTLMAuthenticationCallback {
    private static volatile NTLMAuthenticationCallback callback;

    public static void setNTLMAuthenticationCallback(
            NTLMAuthenticationCallback callback) {
        NTLMAuthenticationCallback.callback = callback;
    }

    public static NTLMAuthenticationCallback getNTLMAuthenticationCallback() {
        return callback;
    }

    /**
     * Returns true if the given site is trusted, i.e. we can try
     * transparent Authentication.
     */
    public abstract boolean isTrustedSite(URL url);
}


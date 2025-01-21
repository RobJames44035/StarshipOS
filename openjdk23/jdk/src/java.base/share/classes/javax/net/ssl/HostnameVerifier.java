/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package javax.net.ssl;

/**
 * This class is the base interface for hostname verification.
 * <P>
 * During handshaking, if the URL's hostname and
 * the server's identification hostname mismatch, the
 * verification mechanism can call back to implementers of this
 * interface to determine if this connection should be allowed.
 * <P>
 * The policies can be certificate-based
 * or may depend on other authentication schemes.
 * <P>
 * These callbacks are used when the default rules for URL hostname
 * verification fail.
 *
 * @author Brad R. Wetmore
 * @since 1.4
 */

public interface HostnameVerifier {
    /**
     * Verify that the host name is an acceptable match with
     * the server's authentication scheme.
     *
     * @param hostname the host name
     * @param session SSLSession used on the connection to host
     * @return true if the host name is acceptable
     */
    boolean verify(String hostname, SSLSession session);
}

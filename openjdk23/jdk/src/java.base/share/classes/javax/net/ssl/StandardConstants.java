/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package javax.net.ssl;

/**
 * Standard constants definitions
 *
 * @since 1.8
 */
public final class StandardConstants {

    // Suppress default constructor for noninstantiability
    private StandardConstants() {
        throw new AssertionError(
            "No javax.net.ssl.StandardConstants instances for you!");
    }

    /**
     * The "host_name" type representing of a DNS hostname
     * (see {@link SNIHostName}) in a Server Name Indication (SNI) extension.
     * <P>
     * The SNI extension is a feature that extends the SSL/TLS protocols to
     * indicate what server name the client is attempting to connect to during
     * handshaking.  See section 3, "Server Name Indication", of <A
     * HREF="http://www.ietf.org/rfc/rfc6066.txt">TLS Extensions (RFC 6066)</A>.
     * <P>
     * The value of this constant is {@value}.
     *
     * @spec https://www.rfc-editor.org/info/rfc6066
     *      RFC 6066: Transport Layer Security (TLS) Extensions: Extension Definitions
     * @see SNIServerName
     * @see SNIHostName
     */
    public static final int SNI_HOST_NAME = 0x00;
}

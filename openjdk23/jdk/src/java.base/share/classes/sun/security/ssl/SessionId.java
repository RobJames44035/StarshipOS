/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package sun.security.ssl;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.net.ssl.SSLProtocolException;

/**
 * Encapsulates an SSL session ID.
 *
 * @author Satish Dharmaraj
 * @author David Brownell
 */
final class SessionId {
    static final int MAX_LENGTH = 32;
    private final byte[] sessionId;          // max 32 bytes

    // Constructs a new session ID ... perhaps for a rejoinable session
    SessionId(boolean isRejoinable, SecureRandom generator) {
        if (isRejoinable && (generator != null)) {
            sessionId = new RandomCookie(generator).randomBytes;
        } else {
            sessionId = new byte[0];
        }
    }

    // Constructs a session ID from a byte array (max size 32 bytes)
    SessionId(byte[] sessionId) {
        this.sessionId = sessionId.clone();
    }

    // Returns the length of the ID, in bytes
    int length() {
        return sessionId.length;
    }

    // Returns the bytes in the ID.  May be an empty array.
    byte[] getId() {
        return sessionId.clone();
    }

    // Returns the ID as a string
    @Override
    public String toString() {
        if (sessionId.length == 0) {
            return "";
        }

        return Utilities.toHexString(sessionId);
    }


    // Returns a value which is the same for session IDs which are equal
    @Override
    public int hashCode() {
        return Arrays.hashCode(sessionId);
    }

    // Returns true if the parameter is the same session ID
    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof SessionId that) {
            return MessageDigest.isEqual(this.sessionId, that.sessionId);
        }

        return false;
    }

    /**
     * Checks the length of the session ID to make sure it sits within
     * the range called out in the specification
     */
    void checkLength(int protocolVersion) throws SSLProtocolException {
        // As of today all versions of TLS have a 32-byte maximum length.
        // In the future we can do more here to support protocol versions
        // that may have longer max lengths.
        if (sessionId.length > MAX_LENGTH) {
            throw new SSLProtocolException("Invalid session ID length (" +
                    sessionId.length + " bytes)");
        }
    }
}

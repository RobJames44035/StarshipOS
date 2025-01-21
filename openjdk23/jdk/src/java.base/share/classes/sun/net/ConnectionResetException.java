/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.net;

import java.net.SocketException;

/**
 * Thrown to indicate a connection reset.
 *
 * @since   1.4.1
 */
public
class ConnectionResetException extends SocketException {
    @java.io.Serial
    private static final long serialVersionUID = -7633185991801851556L;

    public ConnectionResetException(String msg) {
        super(msg);
    }

    public ConnectionResetException() {
    }
}

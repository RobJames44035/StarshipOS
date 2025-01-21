/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;
import java.nio.ByteBuffer;

interface SSLConsumer {
    void consume(ConnectionContext context,
            ByteBuffer message) throws IOException;
}


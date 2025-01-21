/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package sun.nio.ch;

import java.nio.channels.spi.AsynchronousChannelProvider;

/**
 * Creates this platform's default AsynchronousChannelProvider
 */

public class DefaultAsynchronousChannelProvider {

    /**
     * Prevent instantiation.
     */
    private DefaultAsynchronousChannelProvider() { }

    /**
     * Returns the default AsynchronousChannelProvider.
     */
    public static AsynchronousChannelProvider create() {
        return new AixAsynchronousChannelProvider();
    }
}

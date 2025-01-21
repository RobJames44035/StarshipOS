/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.nio.ch;

/**
 * Creates this platform's default SelectorProvider
 */

public class DefaultSelectorProvider {
    private static final SelectorProviderImpl INSTANCE = new KQueueSelectorProvider();

    /**
     * Prevent instantiation.
     */
    private DefaultSelectorProvider() { }

    /**
     * Returns the default SelectorProvider implementation.
     */
    public static SelectorProviderImpl get() {
        return INSTANCE;
    }
}

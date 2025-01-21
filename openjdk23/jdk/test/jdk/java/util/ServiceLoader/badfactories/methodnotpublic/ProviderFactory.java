/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p;

/**
 * Not a provider factory because the static provider() method is not public.
 */

public class ProviderFactory {
    ProviderFactory() { }

    static Service provider() {
        throw new RuntimeException("Should not be called");
    }
}


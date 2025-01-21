/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.net.spi.InetAddressResolverProvider;

module simple.provider {
    exports impl;
    requires java.logging;
    requires test.library;
    provides InetAddressResolverProvider with impl.SimpleResolverProviderImpl;
}

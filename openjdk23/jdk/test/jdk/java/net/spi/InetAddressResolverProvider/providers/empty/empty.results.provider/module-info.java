/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.net.spi.InetAddressResolverProvider;

module empty.results.provider {
    exports impl;
    provides InetAddressResolverProvider with impl.EmptyResultsProviderImpl;
}

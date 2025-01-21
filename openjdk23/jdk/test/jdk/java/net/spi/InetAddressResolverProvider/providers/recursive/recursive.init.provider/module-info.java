/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.net.spi.InetAddressResolverProvider;

module recursive.init.provider {
    exports impl;
    requires java.logging;
    provides InetAddressResolverProvider with impl.InetAddressUsageInGetProviderImpl;
}

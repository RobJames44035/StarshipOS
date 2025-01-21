/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.net;

import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;
import java.util.function.Supplier;

public final class ResolverProviderConfiguration implements
        InetAddressResolverProvider.Configuration {

    private final InetAddressResolver builtinResolver;
    private final Supplier<String> localHostNameSupplier;

    public ResolverProviderConfiguration(InetAddressResolver builtinResolver,
                                         Supplier<String> localHostNameSupplier) {
        this.builtinResolver = builtinResolver;
        this.localHostNameSupplier = localHostNameSupplier;
    }

    @Override
    public InetAddressResolver builtinResolver() {
        return builtinResolver;
    }

    @Override
    public String lookupLocalHostName() {
        return localHostNameSupplier.get();
    }
}

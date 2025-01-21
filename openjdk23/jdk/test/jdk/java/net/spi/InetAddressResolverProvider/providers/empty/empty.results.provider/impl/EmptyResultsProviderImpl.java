/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;
import java.util.stream.Stream;

public class EmptyResultsProviderImpl extends InetAddressResolverProvider {
    @Override
    public InetAddressResolver get(Configuration configuration) {
        System.out.println("The following provider will be used by current test:" +
                this.getClass().getCanonicalName());

        return new InetAddressResolver() {
            @Override
            public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy)
                    throws UnknownHostException {
                return Stream.empty();
            }

            @Override
            public String lookupByAddress(byte[] addr) throws UnknownHostException {
                return configuration.builtinResolver().lookupByAddress(addr);
            }
        };
    }

    @Override
    public String name() {
        return "EmptyForwardLookupResultsProvider";
    }
}

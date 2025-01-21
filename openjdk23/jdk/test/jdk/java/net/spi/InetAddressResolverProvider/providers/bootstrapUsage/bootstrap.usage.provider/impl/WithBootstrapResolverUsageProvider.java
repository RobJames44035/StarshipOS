/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;
import java.util.stream.Stream;

public class WithBootstrapResolverUsageProvider extends InetAddressResolverProvider {

    public static volatile long numberOfGetCalls;

    @Override
    public InetAddressResolver get(Configuration configuration) {
        numberOfGetCalls++;
        System.out.println("The following provider will be used by current test:" +
                this.getClass().getCanonicalName());
        System.out.println("InetAddressResolverProvider::get() called " + numberOfGetCalls + " times");

        // We use different names to avoid InetAddress-level caching
        doLookup("foo" + numberOfGetCalls + ".A.org");

        // We need second call to test how InetAddress internals maintain reference to a bootstrap resolver
        doLookup("foo" + numberOfGetCalls + ".B.org");

        return new InetAddressResolver() {
            @Override
            public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy)
                    throws UnknownHostException {
                return Stream.of(InetAddress.getByAddress(host, new byte[]{127, 0, 2, 1}));
            }

            @Override
            public String lookupByAddress(byte[] addr) throws UnknownHostException {
                return configuration.builtinResolver().lookupByAddress(addr);
            }
        };
    }

    // Perform an InetAddress resolution lookup operation
    private static void doLookup(String hostName) {
        try {
            InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            // Ignore UHE since the bootstrap resolver is used here
        }
    }

    @Override
    public String name() {
        return "WithBootstrapResolverUsageProvider";
    }
}

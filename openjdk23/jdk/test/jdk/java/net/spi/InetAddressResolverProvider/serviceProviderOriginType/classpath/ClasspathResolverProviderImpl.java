/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolverProvider;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolver.LookupPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import testlib.ResolutionRegistry;

public class ClasspathResolverProviderImpl extends InetAddressResolverProvider {

    public static ResolutionRegistry registry = new ResolutionRegistry();
    private static List<LookupPolicy> LOOKUP_HISTORY = Collections.synchronizedList(new ArrayList<>());
    private static Logger LOGGER = Logger.getLogger(ClasspathResolverProviderImpl.class.getName());

    @Override
    public InetAddressResolver get(Configuration configuration) {
        System.out.println("The following provider will be used by current test:" + this.getClass().getCanonicalName());
        return new InetAddressResolver() {
            @Override
            public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy) throws UnknownHostException {
                LOGGER.info("Looking-up addresses for '" + host + "'. Lookup characteristics:" +
                        Integer.toString(lookupPolicy.characteristics(), 2));
                LOOKUP_HISTORY.add(lookupPolicy);
                return registry.lookupHost(host, lookupPolicy);
            }

            @Override
            public String lookupByAddress(byte[] addr) throws UnknownHostException {
                LOGGER.info("Looking host name for the following address:" + ResolutionRegistry.addressBytesToString(addr));
                return registry.lookupAddress(addr);
            }
        };
    }

    @Override
    public String name() {
        return "classpathINSP";
    }
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package impl;

import java.net.spi.InetAddressResolverProvider;
import java.net.spi.InetAddressResolver;

public class FaultyResolverProviderGetImpl extends InetAddressResolverProvider {
    public static final String EXCEPTION_MESSAGE = "This provider provides nothing";

    @Override
    public InetAddressResolver get(Configuration configuration) {
        System.out.println("The following provider will be used by current test:" + this.getClass().getCanonicalName());
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    @Override
    public String name() {
        return "faultyInetAddressResolverGet";
    }
}

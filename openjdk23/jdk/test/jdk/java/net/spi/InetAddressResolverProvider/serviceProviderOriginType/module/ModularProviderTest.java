/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.net.InetAddress;

import org.testng.annotations.Test;

/*
 * @test
 * @summary Test that implementation of InetAddressResolverProvider can be installed to a module path.
 * @library ../../lib ../../providers/simple
 * @build test.library/testlib.ResolutionRegistry simple.provider/impl.SimpleResolverProviderImpl
 *        ModularProviderTest
 * @run testng/othervm ModularProviderTest
 */


public class ModularProviderTest {

    @Test
    public void testResolution() throws Exception {
        InetAddress inetAddress = InetAddress.getByName("modular-provider-test.org");
        System.err.println("Resolved address:" + inetAddress);

        if (!impl.SimpleResolverProviderImpl.registry.containsAddressMapping(inetAddress)) {
            throw new RuntimeException("InetAddressResolverProvider was not properly installed");
        }
    }
}

/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import org.testng.Assert;
import org.testng.annotations.Test;
import sun.net.spi.DefaultProxySelector;

import java.net.ProxySelector;
import java.net.URI;

/**
 * @test
 * @bug 6563286 6797318 8177648
 * @summary Tests sun.net.spi.DefaultProxySelector#select(URI)
 * @run testng DefaultProxySelectorTest
 * @modules java.base/sun.net.spi:+open
 */
public class DefaultProxySelectorTest {

    /**
     * Tests that {@link DefaultProxySelector#select(URI)} throws
     * {@link IllegalArgumentException} when passed {@code null}
     */
    @Test
    public void testIllegalArgForNull() {
        final ProxySelector selector = new DefaultProxySelector();
        try {
            selector.select(null);
            Assert.fail("select() was expected to fail for null URI");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    /**
     * Tests that {@link DefaultProxySelector} throws a {@link IllegalArgumentException}
     * for URIs that don't have host information
     *
     * @throws Exception
     */
    @Test
    public void testIllegalArgForNoHost() throws Exception {
        final ProxySelector selector = new DefaultProxySelector();
        assertFailsWithIAE(selector, new URI("http", "/test", null));
        assertFailsWithIAE(selector, new URI("https", "/test2", null));
        assertFailsWithIAE(selector, new URI("ftp", "/test3", null));
    }


    /**
     * Tests that {@link DefaultProxySelector} throws a {@link IllegalArgumentException}
     * for URIs that don't have protocol/scheme information
     *
     * @throws Exception
     */
    @Test
    public void testIllegalArgForNoScheme() throws Exception {
        final ProxySelector selector = new DefaultProxySelector();
        assertFailsWithIAE(selector, new URI(null, "/test", null));
    }

    private static void assertFailsWithIAE(final ProxySelector selector, final URI uri) {
        try {
            selector.select(uri);
            Assert.fail("select() was expected to fail for URI " + uri);
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

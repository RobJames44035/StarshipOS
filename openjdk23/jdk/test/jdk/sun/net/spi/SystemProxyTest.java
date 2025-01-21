/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

import sun.net.spi.DefaultProxySelector;

// this test is launched from SystemProxyDriver
public class SystemProxyTest {

    // calls the DefaultProxySelector.select(URI) and verifies that the returned List is
    // not null, not empty and doesn't contain null elements.
    public static void main(final String[] args) throws Exception {
        final ProxySelector ps = new DefaultProxySelector();
        final URI uri = new URI("http://example.com"); // the target URL doesn't matter
        final List<Proxy> proxies = ps.select(uri);
        if (proxies == null) {
            // null isn't expected to be returned by the select() API
            throw new AssertionError("DefaultProxySelector.select(URI) returned null for uri: "
                    + uri);
        }
        if (proxies.isEmpty()) {
            // empty list isn't expected to be returned by the select() API, instead when
            // no proxy is configured, the returned list is expected to contain one entry with
            // a Proxy instance representing direct connection
            throw new AssertionError("DefaultProxySelector.select(URI) returned empty list" +
                    " for uri: " + uri);
        }
        System.out.println("returned proxies list: " + proxies);
        for (final Proxy p : proxies) {
            if (p == null) {
                throw new AssertionError("null proxy in proxies list for uri: " + uri);
            }
        }
    }
}

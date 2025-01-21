/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8133686
 * @summary Ensuring that multiple header values for a given field-name are returned in
 *          the order they were added for HttpURLConnection.getRequestProperties
 *          and HttpURLConnection.getHeaderFields
 * @library /test/lib
 * @run testng URLConnectionHeadersOrder
 */

import jdk.test.lib.net.URIBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class URLConnectionHeadersOrder {
    @Test
    public void testRequestPropertiesOrder() throws Exception {
        var url = URIBuilder.newBuilder()
                .scheme("http")
                .host(InetAddress.getLoopbackAddress())
                .toURL();

        var conn = new DummyHttpURLConnection(url);
        conn.addRequestProperty("test", "a");
        conn.addRequestProperty("test", "b");
        conn.addRequestProperty("test", "c");
        conn.connect();

        var expectedRequestProps = Arrays.asList("a", "b", "c");
        var actualRequestProps = conn.getRequestProperties().get("test");

        Assert.assertNotNull(actualRequestProps);

        String errorMessageTemplate = "Expected Request Properties = %s, Actual Request Properties = %s";
        Assert.assertEquals(actualRequestProps, expectedRequestProps, String.format(errorMessageTemplate, expectedRequestProps.toString(), actualRequestProps.toString()));
    }
}

class DummyHttpURLConnection extends URLConnection {

    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * @param url the specified URL.
     */
    protected DummyHttpURLConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        var connected = true;
    }
}
/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4485208 8252767
 * @summary Validate various request property methods on java.net.URLConnection
 * throw NullPointerException and IllegalStateException when expected
 * @run testng RequestProperties
 */

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RequestProperties {

    private static final Class NPE = NullPointerException.class;
    private static final Class ISE = IllegalStateException.class;

    @DataProvider(name = "urls")
    private Object[][] urls() {
        final List<String> urls = new ArrayList<>();
        urls.add("http://foo.com/bar/");
        urls.add("jar:http://foo.com/bar.html!/foo/bar");
        urls.add("file:/etc/passwd");
        if (hasFtp()) {
            urls.add("ftp://foo:bar@foobar.com/etc/passwd");
        }
        final Object[][] data = new Object[urls.size()][1];
        for (int i = 0; i < urls.size(); i++) {
            data[i][0] = urls.get(i);
        }
        return data;
    }


    /**
     * Test that {@link java.net.URLConnection#setRequestProperty(String, String)} throws
     * a {@link NullPointerException} when passed null key
     */
    @Test(dataProvider = "urls")
    public void testSetRequestPropertyNullPointerException(final String url) throws Exception {
        final URLConnection conn = new URL(url).openConnection();
        Assert.assertThrows(NPE, () -> conn.setRequestProperty(null, "bar"));
        // expected to pass
        conn.setRequestProperty("key", null);
    }

    /**
     * Test that {@link java.net.URLConnection#addRequestProperty(String, String)} throws
     * a {@link NullPointerException} when passed null key
     */
    @Test(dataProvider = "urls")
    public void testAddRequestPropertyNullPointerException(final String url) throws Exception {
        final URLConnection conn = new URL(url).openConnection();
        Assert.assertThrows(NPE, () -> conn.addRequestProperty(null, "hello"));
        // expected to pass
        conn.addRequestProperty("key", null);
    }

    /**
     * Test that {@link java.net.URLConnection#getRequestProperty(String)} returns
     * null when the passed key is null
     */
    @Test(dataProvider = "urls")
    public void testGetRequestPropertyReturnsNull(final String url) throws Exception {
        final URLConnection conn = new URL(url).openConnection();
        Assert.assertNull(conn.getRequestProperty(null),
                "getRequestProperty was expected to return null for null key");
    }

    /**
     * Test that {@link java.net.URLConnection#setRequestProperty(String, String)} throws
     * an {@link IllegalStateException} when already connected
     */
    @Test
    public void testSetRequestPropertyIllegalStateException() throws Exception {
        final URLConnection conn = createAndConnectURLConnection();
        try {
            Assert.assertThrows(ISE, () -> conn.setRequestProperty("foo", "bar"));
        } finally {
            safeClose(conn);
        }
    }

    /**
     * Test that {@link java.net.URLConnection#addRequestProperty(String, String)} throws
     * an {@link IllegalStateException} when already connected
     */
    @Test
    public void testAddRequestPropertyIllegalStateException() throws Exception {
        final URLConnection conn = createAndConnectURLConnection();
        try {
            Assert.assertThrows(ISE, () -> conn.addRequestProperty("foo", "bar"));
        } finally {
            safeClose(conn);
        }
    }

    /**
     * Test that {@link java.net.URLConnection#getRequestProperty(String)} throws
     * an {@link IllegalStateException} when already connected
     */
    @Test
    public void testGetRequestPropertyIllegalStateException() throws Exception {
        final URLConnection conn = createAndConnectURLConnection();
        try {
            Assert.assertThrows(ISE, () -> conn.getRequestProperty("hello"));
        } finally {
            safeClose(conn);
        }
    }

    /**
     * Test that {@link URLConnection#getRequestProperties()} throws
     * an {@link IllegalStateException} when already connected
     */
    @Test
    public void testGetRequestPropertiesIllegalStateException() throws Exception {
        final URLConnection conn = createAndConnectURLConnection();
        try {
            Assert.assertThrows(ISE, () -> conn.getRequestProperties());
        } finally {
            safeClose(conn);
        }
    }

    private static URLConnection createAndConnectURLConnection() throws IOException {
        final URL url = Path.of(System.getProperty("java.io.tmpdir")).toUri().toURL();
        final URLConnection conn = url.openConnection();
        conn.connect();
        return conn;
    }

    private static void safeClose(final URLConnection conn) {
        try {
            conn.getInputStream().close();
        } catch (Exception e) {
            // ignore
        }
    }

    private static boolean hasFtp() {
        try {
            new java.net.URL("ftp://");
            return true;
        } catch (java.net.MalformedURLException x) {
            System.out.println("FTP not supported by this runtime.");
            return false;
        }
    }
}

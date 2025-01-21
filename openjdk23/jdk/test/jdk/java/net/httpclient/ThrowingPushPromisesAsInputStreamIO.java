/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8229822
 * @summary Tests what happens when push promise handlers and their
 *          response body handlers and subscribers throw unexpected exceptions.
 * @library /test/lib /test/jdk/java/net/httpclient/lib
 * @build jdk.test.lib.net.SimpleSSLContext
 *        ReferenceTracker AbstractThrowingPushPromises ThrowingPushPromisesAsInputStreamIO
 *        jdk.httpclient.test.lib.common.HttpServerAdapters
 * @run testng/othervm -Djdk.internal.httpclient.debug=true ThrowingPushPromisesAsInputStreamIO
 */

import org.testng.annotations.Test;

public class ThrowingPushPromisesAsInputStreamIO extends AbstractThrowingPushPromises {

    @Test(dataProvider = "ioVariants")
    public void testThrowingAsInputStream(String uri, boolean sameClient, Thrower thrower)
            throws Exception {
        super.testThrowingAsInputStreamImpl(uri, sameClient, thrower);
    }
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Tests what happens when response body handlers and subscribers
 *          throw unexpected exceptions.
 * @library /test/lib /test/jdk/java/net/httpclient/lib
 * @build jdk.test.lib.net.SimpleSSLContext
 *        ReferenceTracker ThrowingSubscribersAsInputStreamAsync AbstractThrowingSubscribers
 *        jdk.httpclient.test.lib.common.HttpServerAdapters
 * @run testng/othervm -Djdk.internal.httpclient.debug=true ThrowingSubscribersAsInputStreamAsync
 */

import org.testng.annotations.Test;

public class ThrowingSubscribersAsInputStreamAsync extends AbstractThrowingSubscribers {

    @Test(dataProvider = "variants")
    public void testThrowingAsInputStreamAsync(String uri, boolean sameClient, Thrower thrower)
            throws Exception {
        super.testThrowingAsInputStreamAsyncImpl(uri, sameClient, thrower);
    }

}

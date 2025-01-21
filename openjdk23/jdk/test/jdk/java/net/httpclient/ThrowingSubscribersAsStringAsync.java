/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Tests what happens when response body handlers and subscribers
 *          throw unexpected exceptions.
 * @library /test/lib /test/jdk/java/net/httpclient/lib
 * @build jdk.test.lib.net.SimpleSSLContext
 *        ReferenceTracker ThrowingSubscribersAsStringAsync AbstractThrowingSubscribers
 *        jdk.httpclient.test.lib.common.HttpServerAdapters
 * @run testng/othervm -Djdk.internal.httpclient.debug=true ThrowingSubscribersAsStringAsync
 */

import org.testng.annotations.Test;

public class ThrowingSubscribersAsStringAsync extends AbstractThrowingSubscribers {

    @Test(dataProvider = "variants")
    public void testThrowingAsStringAsync(String uri, boolean sameClient, Thrower thrower)
            throws Exception {
        super.testThrowingAsStringAsyncImpl(uri, sameClient, thrower);
    }

}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Tests what happens when response body handlers and subscribers
 *          throw unexpected exceptions.
 * @library /test/lib /test/jdk/java/net/httpclient/lib
 * @build jdk.test.lib.net.SimpleSSLContext
 *        ReferenceTracker ThrowingSubscribersAsString AbstractThrowingSubscribers
 *        jdk.httpclient.test.lib.common.HttpServerAdapters
 * @run testng/othervm -Djdk.internal.httpclient.debug=true ThrowingSubscribersAsString
 */

import org.testng.annotations.Test;

public class ThrowingSubscribersAsString extends AbstractThrowingSubscribers {

    @Test(dataProvider = "variants")
    public void testThrowingAsString(String uri, boolean sameClient, Thrower thrower)
            throws Exception {
        super.testThrowingAsStringImpl(uri, sameClient, thrower);
    }

}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Tests what happens when request publishers
 *          throw unexpected exceptions.
 * @library /test/lib /test/jdk/java/net/httpclient/lib
 * @build jdk.test.lib.net.SimpleSSLContext
 *        ReferenceTracker AbstractThrowingPublishers ThrowingPublishersInRequest
 *        jdk.httpclient.test.lib.common.HttpServerAdapters
 * @run testng/othervm -Djdk.internal.httpclient.debug=true
 *                     -Djdk.httpclient.enableAllMethodRetry=true
 *                     ThrowingPublishersInRequest
 */

import org.testng.annotations.Test;

import java.util.Set;

public class ThrowingPublishersInRequest extends AbstractThrowingPublishers {

    @Test(dataProvider = "requestProvider")
    public void testThrowingAsString(String uri, boolean sameClient,
                                            Thrower thrower, Set<Where> whereValues)
            throws Exception
    {
        super.testThrowingAsStringImpl(uri, sameClient, thrower, whereValues);
    }
}

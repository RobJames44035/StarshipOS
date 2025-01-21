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
 *        ReferenceTracker AbstractThrowingPushPromises ThrowingPushPromisesAsLinesCustom
 *        jdk.httpclient.test.lib.common.HttpServerAdapters
 * @run testng/othervm -Djdk.internal.httpclient.debug=true ThrowingPushPromisesAsLinesCustom
 */

import org.testng.annotations.Test;

public class ThrowingPushPromisesAsLinesCustom extends AbstractThrowingPushPromises {

    @Test(dataProvider = "customVariants")
    public void testThrowingAsLines(String uri, boolean sameClient, Thrower thrower)
            throws Exception {
        super.testThrowingAsLinesImpl(uri, sameClient, thrower);
    }
}

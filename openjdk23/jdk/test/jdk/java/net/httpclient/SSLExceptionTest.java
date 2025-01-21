/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.security.NoSuchAlgorithmException;
import org.testng.annotations.Test;
import static org.testng.Assert.expectThrows;
import static org.testng.Assert.fail;

/*
 * @test
 * @bug 8251715
 * @summary This test verifies exception when resources for
 * SSLcontext used by HttpClient are not available
 * @build SSLExceptionTest
 * @run testng/othervm -Djdk.tls.client.protocols="InvalidTLSv1.4"
 *                      SSLExceptionTest
 */

public class SSLExceptionTest  {

    Throwable excp,noSuchAlgo;

    static final int ITERATIONS = 10;

    @Test
    public void testHttpClientsslException() {
        for (int i = 0; i < ITERATIONS; i++) {
            excp = expectThrows(UncheckedIOException.class, HttpClient.newBuilder()::build);
            noSuchAlgo = excp.getCause().getCause();
            if ( !(noSuchAlgo instanceof NoSuchAlgorithmException) ) {
                fail("Test failed due to wrong exception cause : " + noSuchAlgo);
            }
            excp = expectThrows(UncheckedIOException.class, HttpClient::newHttpClient);
            noSuchAlgo = excp.getCause().getCause();
            if ( !(noSuchAlgo instanceof NoSuchAlgorithmException) ) {
                fail("Test failed due to wrong exception cause : " + noSuchAlgo);
            }
        }
    }
}

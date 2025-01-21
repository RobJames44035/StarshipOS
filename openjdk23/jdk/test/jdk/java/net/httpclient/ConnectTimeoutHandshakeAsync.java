/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.net.http.HttpClient.Version;
import java.time.Duration;
import org.testng.annotations.Test;

/*
 * @test
 * @summary Tests connection timeouts during SSL handshake
 * @bug 8208391
 * @library /test/lib
 * @build AbstractConnectTimeoutHandshake
 * @run testng/othervm ConnectTimeoutHandshakeAsync
 */

public class ConnectTimeoutHandshakeAsync
    extends AbstractConnectTimeoutHandshake
{
    @Test(dataProvider = "variants")
    @Override
    public void timeoutAsync(Version requestVersion,
                             String method,
                             Duration connectTimeout,
                             Duration requestTimeout) {
        super.timeoutAsync(requestVersion, method, connectTimeout, requestTimeout);
    }
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.net.http.HttpClient.Version;
import java.time.Duration;
import org.testng.annotations.Test;

/*
 * @test
 * @summary Tests for connection related timeouts
 * @bug 8208391
 * @run testng/othervm ConnectTimeoutNoProxyAsync
 */

public class ConnectTimeoutNoProxyAsync extends AbstractConnectTimeout {

    @Test(dataProvider = "variants")
    @Override
    public void timeoutNoProxyAsync(Version requestVersion,
                                    String scheme,
                                    String method,
                                    Duration connectTimeout,
                                    Duration requestduration)
    {
        super.timeoutNoProxyAsync(requestVersion, scheme, method, connectTimeout, requestduration);
    }
}

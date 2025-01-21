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
 * @run testng/othervm ConnectTimeoutNoProxySync
 */

public class ConnectTimeoutNoProxySync extends AbstractConnectTimeout {

    @Test(dataProvider = "variants")
    @Override
    public void timeoutNoProxySync(Version requestVersion,
                                   String scheme,
                                   String method,
                                   Duration connectTimeout,
                                   Duration requestTimeout)
        throws Exception
    {
        super.timeoutNoProxySync(requestVersion, scheme, method, connectTimeout, requestTimeout);
    }
}

/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.httpclient.test.lib.http2;

import javax.net.ssl.SSLSession;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpHeaders;
import jdk.internal.net.http.common.HttpHeadersBuilder;

/**
 * A supplier of Http2TestExchanges. If the default Http2TestExchange impl is
 * not sufficient, then a supplier may be set on an Http2TestServer through its
 * {@link Http2TestServer#setExchangeSupplier(Http2TestExchangeSupplier)}.
 *
 * Useful for testing scenarios where non-standard or specific server behaviour
 * is required, either direct control over the frames sent, "bad" behaviour, or
 * something else.
 */
public interface Http2TestExchangeSupplier {

    Http2TestExchange get(int streamid,
                          String method,
                          HttpHeaders reqheaders,
                          HttpHeadersBuilder rspheadersBuilder,
                          URI uri,
                          InputStream is,
                          SSLSession sslSession,
                          BodyOutputStream os,
                          Http2TestServerConnection conn,
                          boolean pushAllowed);

    static Http2TestExchangeSupplier ofDefault() {
        return Http2TestExchangeImpl::new;
    }
}

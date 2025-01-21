/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.httpclient.test.lib.http2;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.List;

import jdk.internal.net.http.frame.ContinuationFrame;
import jdk.internal.net.http.frame.Http2Frame;

// will be converted to a PushPromiseFrame in the writeLoop
// a thread is then created to produce the DataFrames from the InputStream
public class OutgoingPushPromise extends Http2Frame {
    final HttpHeaders headers;
    final URI uri;
    final InputStream is;
    final int parentStream; // not the pushed streamid
    private final List<Http2Frame> continuations;

    public OutgoingPushPromise(int parentStream,
                               URI uri,
                               HttpHeaders headers,
                               InputStream is) {
        this(parentStream, uri, headers, is, List.of());
    }

    public OutgoingPushPromise(int parentStream,
                               URI uri,
                               HttpHeaders headers,
                               InputStream is,
                               List<ContinuationFrame> continuations) {
        super(0,0);
        this.uri = uri;
        this.headers = headers;
        this.is = is;
        this.parentStream = parentStream;
        this.continuations = List.copyOf(continuations);
    }

    public List<Http2Frame> getContinuations() {
        return continuations;
    }
}

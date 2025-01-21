/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * An HttpClient that delegates all its operations to the given client.
 */
public class DelegatingHttpClient extends HttpClient {

    private final HttpClient client;

    public DelegatingHttpClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public Optional<CookieHandler> cookieHandler() {
        return client.cookieHandler();
    }

    @Override
    public Optional<Duration> connectTimeout() {
        return client.connectTimeout();
    }

    @Override
    public Redirect followRedirects() {
        return client.followRedirects();
    }

    @Override
    public Optional<ProxySelector> proxy() {
        return client.proxy();
    }

    @Override
    public SSLContext sslContext() {
        return client.sslContext();
    }

    @Override
    public SSLParameters sslParameters() {
        return client.sslParameters();
    }

    @Override
    public Optional<Authenticator> authenticator() {
        return client.authenticator();
    }

    @Override
    public Version version() {
        return client.version();
    }

    @Override
    public Optional<Executor> executor() {
        return client.executor();
    }

    @Override
    public <T> HttpResponse<T> send(HttpRequest request,
                                    HttpResponse.BodyHandler<T> responseBodyHandler)
            throws IOException, InterruptedException {
        return client.send(request, responseBodyHandler);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>>
    sendAsync(HttpRequest request,
              HttpResponse.BodyHandler<T> responseBodyHandler) {
        return client.sendAsync(request, responseBodyHandler);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>>
    sendAsync(HttpRequest request,
              HttpResponse.BodyHandler<T> responseBodyHandler,
              HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
        return client.sendAsync(request, responseBodyHandler, pushPromiseHandler);
    }

    @Override
    public boolean isTerminated() {
        return client.isTerminated();
    }

    @Override
    public void shutdown() {
        client.shutdown();
    }

    @Override
    public void shutdownNow() {
        client.shutdownNow();
    }

    @Override
    public boolean awaitTermination(Duration duration) throws InterruptedException {
        return client.awaitTermination(duration);
    }

    @Override
    public void close() { client.close(); }

    /**
     * Invokes the default implementation of HttpClient::close
     * Can be invoked by subclasses if necessary.
     */
    protected void defaultClose() { super.close();}

}

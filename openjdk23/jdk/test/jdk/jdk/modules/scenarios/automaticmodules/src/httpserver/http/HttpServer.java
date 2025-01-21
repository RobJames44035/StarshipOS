/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package http;

import java.util.Iterator;
import java.util.ServiceLoader;

import http.spi.HttpServerProvider;
import logging.Logger;

/**
 * A do-nothing HTTP server
 */

public class HttpServer {
    private final int port;
    private final Logger logger;

    protected HttpServer(int port) {
        this.port = port;
        this.logger = new Logger();
    }

    public static HttpServer create(int port) {
        ServiceLoader<HttpServerProvider> sl
            = ServiceLoader.load(HttpServerProvider.class);
        Iterator<HttpServerProvider> iterator = sl.iterator();
        if (iterator.hasNext()) {
            HttpServerProvider provider = iterator.next();
            return provider.createHttpServer(port);
        } else {
            return new HttpServer(port) { };
        }
    }

    public void start() {
        logger.log("Start HTTP server on port " + port);
    }
}

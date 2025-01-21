/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public abstract class MyHttpHandlerB implements HttpHandler {
    // This class doesn't implement handle(), so it can be linked even
    // if the App loader resolves a different HttpExchange than the Platform loader.

    /* public void handle(HttpExchange exchange) {} */

    static void touch() {

    }

    static public void test(HttpHandler handler) throws IOException {
        // This method call must fail to link.
        handler.handle(null);
    }
}


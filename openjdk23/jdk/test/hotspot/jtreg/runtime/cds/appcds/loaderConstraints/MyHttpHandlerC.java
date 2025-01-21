/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class MyHttpHandlerC implements HttpHandler {
    public MyHttpHandlerC() {}

    // This class overrides handle(), but its loader (MyClassLoader) resolves the same
    // HttpExchange as the platfom loader (whose handle() method is overidden), so when
    // MyHttpHandlerC loaded it should be able to link
    public void handle(HttpExchange exchange) {
        throw new RuntimeException("MyHttpHandlerB.test() must not be able to invoke this method");
    }

    static public void test(HttpHandler handler) throws IOException {
        // This method call must fail to link.
        handler.handle(null);
    }
}


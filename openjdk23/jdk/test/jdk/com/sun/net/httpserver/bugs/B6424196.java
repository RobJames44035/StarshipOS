/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6424196
 * @summary  PIT build 85 mustang: two jhttp tests fail
 */

import java.net.InetSocketAddress;
import java.io.IOException;
import com.sun.net.httpserver.*;

public class B6424196 {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
    }
}

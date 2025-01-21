/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/**
 * @test
 * @bug 4147223
 * @summary test URLConnection.GetContentLength for file protocol
 */

import java.net.*;

public class GetContentLength {
    public static void main(String[] args) throws Exception {
        int len;
        URL u = new URL("file:"+System.getProperty("test.src", ".")+
                        "/GetContentLength.java");
        URLConnection urlc = u.openConnection();
        urlc.connect();
        len = urlc.getContentLength();
        if (len<0)
            throw new RuntimeException("GetContentLength returned invalid length.");
    }
}

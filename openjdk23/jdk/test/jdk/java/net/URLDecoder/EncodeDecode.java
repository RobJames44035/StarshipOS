/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4402326
 * @summary  URLDecoder fails with certain input
 */
import java.net.*;

public class EncodeDecode {
    public static void main(String[] args) {
        String str = "fds@$";
        String encStr = URLEncoder.encode(str);
        String decStr = URLDecoder.decode(encStr);
    }
}

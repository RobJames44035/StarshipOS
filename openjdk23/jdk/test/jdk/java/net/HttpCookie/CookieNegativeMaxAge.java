/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8005068
 * @summary Check that any negative maxAge is treated as "unspecified" and
 * if header contains cookie with "expires" attribute in the past then cookie
 * is created with maxAge=0 meaning it is specified to be immediately expired.
 * @run main CookieNegativeMaxAge
 */


import java.net.HttpCookie;
import java.util.List;

public class CookieNegativeMaxAge {

    public static void main(String... args) {
        HttpCookie cookie = new HttpCookie("testCookie", "value");
        cookie.setMaxAge(Integer.MIN_VALUE);
        if (cookie.hasExpired()) {
            throw new RuntimeException("Cookie has unexpectedly expired");
        }

        List<HttpCookie> cookies = HttpCookie.parse("Set-Cookie: " +
                "expiredCookie=value; expires=Thu, 01 Jan 1970 00:00:00 GMT");
        if (cookies.size() == 1) {
            if (cookies.get(0).getMaxAge() != 0) {
                throw new RuntimeException("Cookie maxAge expected to be 0");
            }
        } else {
            throw new RuntimeException("Header was incorrectly parsed");
        }
    }
}

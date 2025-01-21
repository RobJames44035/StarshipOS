/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6953455 7045655
 * @summary CookieStore.add() cannot handle null URI parameter
 *     and An empty InMemoryCookieStore should not return true for removeAll
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class NullUriCookieTest {
    static boolean fail = false;

    public static void main(String[] args) throws Exception {
        checkCookieNullUri();
    }

    static void checkCookieNullUri() throws Exception {
        //get a cookie store implementation and add a cookie to the store with null URI
        CookieStore cookieStore = (new CookieManager()).getCookieStore();
        //Check if removeAll() retrurns false on an empty CookieStore
        if (cookieStore.removeAll()) {
            fail = true;
        }
        checkFail("removeAll on empty store should return false");
        HttpCookie cookie = new HttpCookie("MY_COOKIE", "MY_COOKIE_VALUE");
        cookie.setDomain("foo.com");
        cookieStore.add(null, cookie);

        //Retrieve added cookie
        URI uri = new URI("http://foo.com");
        List<HttpCookie> addedCookieList = cookieStore.get(uri);

        //Verify CookieStore behaves well
        if (addedCookieList.size() != 1) {
           fail = true;
        }
        checkFail("Abnormal size of cookie jar");

        for (HttpCookie chip : addedCookieList) {
            if (!chip.equals(cookie)) {
                 fail = true;
            }
        }
        checkFail("Cookie not retrieved from Cookie Jar");
        boolean ret = cookieStore.remove(null,cookie);
        if (!ret) {
            fail = true;
        }
        checkFail("Abnormal removal behaviour from Cookie Jar");
    }

    static void checkFail(String exp) {
        if (fail) {
            throw new RuntimeException(exp);
        }
    }
}


/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8251496
 * @summary Tests for methods in HttpPrincipal
 * @run testng/othervm HttpPrincipalTest
 */

import com.sun.net.httpserver.HttpPrincipal;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HttpPrincipalTest {

    @Test
    public void testGetters() {
        var principal = new HttpPrincipal("test", "123");

        assertEquals(principal.getUsername(), "test");
        assertEquals(principal.getRealm(), "123");
        assertEquals(principal.getName(), "123:test");
        assertEquals(principal.toString(), principal.getName());
        assertEquals(("test"+"123").hashCode(), principal.hashCode());
    }
}

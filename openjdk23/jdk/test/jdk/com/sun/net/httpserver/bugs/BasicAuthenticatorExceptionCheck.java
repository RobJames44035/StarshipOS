/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8230159 8275534
 * @library /test/lib
 * @summary Ensure that correct exceptions are being thrown in
 *          BasicAuthenticator constructor
 * @run testng BasicAuthenticatorExceptionCheck
 */


import java.nio.charset.Charset;
import com.sun.net.httpserver.BasicAuthenticator;
import org.testng.annotations.Test;

import static org.testng.Assert.expectThrows;
import static org.testng.Assert.assertEquals;
import static java.nio.charset.StandardCharsets.UTF_8;


public class BasicAuthenticatorExceptionCheck {
    static final Class<NullPointerException> NPE = NullPointerException.class;
    static final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;

    static BasicAuthenticator createBasicAuthenticator(String realm, Charset charset) {
        return new BasicAuthenticator(realm, charset) {
            public boolean checkCredentials(String username, String pw) {
                return true;
            }
        };
    }
    static BasicAuthenticator createBasicAuthenticator(String realm) {
        return new BasicAuthenticator(realm) {
            public boolean checkCredentials(String username, String pw) {
                return true;
            }
        };
    }

    @Test
    public void testAuthenticationException() {

        Throwable ex = expectThrows(NPE, () ->
                createBasicAuthenticator("/test", null));
        System.out.println("Valid realm and Null charset provided - " +
                "NullPointerException thrown as expected: " + ex);

        ex = expectThrows(NPE, () ->
                createBasicAuthenticator(null, UTF_8));
        System.out.println("Null realm and valid charset provided - " +
                "NullPointerException thrown as expected: " + ex);

        ex = expectThrows(IAE, () ->
                createBasicAuthenticator("", UTF_8));
        assertEquals(ex.getMessage(), "realm must not be empty");
        System.out.println("Empty string for realm and valid charset provided - " +
                "IllegalArgumentException thrown as expected: " + ex);

        ex = expectThrows(NPE, () ->
                createBasicAuthenticator(null));
        System.out.println("Null realm provided - " +
                "NullPointerException thrown as expected: " + ex);

        ex = expectThrows(IAE, () ->
                createBasicAuthenticator(""));
        assertEquals(ex.getMessage(), "realm must not be empty");
        System.out.println("Empty string for realm provided - " +
                "IllegalArgumentException thrown as expected: " + ex);

        ex = expectThrows(IAE, () ->
                createBasicAuthenticator("\"/test\""));
        assertEquals(ex.getMessage(), "realm invalid: \"/test\"");
        System.out.println("Invalid string for realm provided - " +
                "IllegalArgumentException thrown as expected: " + ex);

        ex = expectThrows(IAE, () ->
                createBasicAuthenticator("\""));
        assertEquals(ex.getMessage(), "realm invalid: \"");
        System.out.println("Invalid string for realm provided - " +
                "IllegalArgumentException thrown as expected: " + ex);

        createBasicAuthenticator("\\\"/test\\\"");
    }
}

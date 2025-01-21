/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8282723
 * @summary Add constructors taking a cause to JSSE exceptions
 */
import javax.net.ssl.SSLKeyException;
import java.util.Objects;

public class CheckSSLKeyException {
    private static String exceptionMessage = "message";
    private static Throwable exceptionCause = new RuntimeException();

    public static void main(String[] args) throws Exception {
        testException(
                new SSLKeyException(exceptionMessage, exceptionCause));
    }

    private static void testException(Exception ex) {
        if (!Objects.equals(ex.getMessage(), exceptionMessage)) {
            throw new RuntimeException("Unexpected exception message");
        }

        if (ex.getCause() != exceptionCause) {
            throw new RuntimeException("Unexpected exception cause");
        }
    }
}

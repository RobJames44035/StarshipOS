/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8282686
 * @summary Verify cause and message handling of SocketException
 */
import java.net.SocketException;
import java.util.Objects;

public class TestSocketExceptionCtor {
    public static void main(String... args) {
        String message = "message";
        Throwable cause = new RuntimeException();

        testException(new SocketException(cause), cause.toString(), cause);
        testException(new SocketException(message, cause), message, cause);
    }

    private static void testException(SocketException se,
                                      String expectedMessage,
                                      Throwable expectedCause) {
        var message = se.getMessage();
        if (!Objects.equals(message, expectedMessage)) {
            throw new RuntimeException("Unexpected message " + message);
        }

        var cause = se.getCause();
        if (cause != expectedCause) {
            throw new RuntimeException("Unexpected cause");
        }
    }
}

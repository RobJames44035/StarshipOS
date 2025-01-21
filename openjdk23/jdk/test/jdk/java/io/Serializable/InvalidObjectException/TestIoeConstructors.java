/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8282696
 * @summary Verify message and cause handling of InvalidObjectException
 */
import java.io.*;
import java.util.Objects;

public class TestIoeConstructors {
    public static void main(String... args) {
        String reason = "reason";
        Throwable cause = new RuntimeException();

        testException(new InvalidObjectException(reason),
                      reason, null);
        testException(new InvalidObjectException(reason, cause),
                      reason, cause);
    }

    private static void testException(InvalidObjectException ioe,
                                      String expectedMessage,
                                      Throwable expectedCause) {
        var message = ioe.getMessage();
        if (!Objects.equals(message, expectedMessage)) {
            throw new RuntimeException("Unexpected message " + message);
        }

        var cause = ioe.getCause();
        if (cause != expectedCause) {
            throw new RuntimeException("Unexpected cause");
        }
    }
}

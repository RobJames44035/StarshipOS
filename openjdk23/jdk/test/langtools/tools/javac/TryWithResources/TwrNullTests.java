/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7020047
 * @summary Test null handling of try-with-resources statement
 */

public class TwrNullTests {
    /*
     * Each try-with-resources statement generates two calls to the
     * close method for each resource: one for when there is a primary
     * exception present and the second for when a primary exception
     * is absent.  The null handling of both cases needs to be
     * checked.
     */
    public static void main(String... args) {
        testNormalCompletion();
        testNoSuppression();
    }

    /*
     * Verify empty try-with-resources on a null resource completes
     * normally; no NPE from the generated close call.
     */
    private static void testNormalCompletion() {
        try(AutoCloseable resource = null) {
            return; // Nothing to see here, move along.
        } catch (Exception e) {
            throw new AssertionError("Should not be reached", e);
        }
    }

    /*
     * Verify that a NPE on a null resource is <em>not</em> added as a
     * suppressed exception to an exception from try block.
     */
    private static void testNoSuppression() {
        try(AutoCloseable resource = null) {
            throw new java.io.IOException();
        } catch(java.io.IOException ioe) {
            Throwable[] suppressed = ioe.getSuppressed();
            if (suppressed.length != 0) {
                throw new AssertionError("Non-empty suppressed exceptions",
                                         ioe);
            }
        } catch (Exception e) {
            throw new AssertionError("Should not be reached", e);
        }
    }
}

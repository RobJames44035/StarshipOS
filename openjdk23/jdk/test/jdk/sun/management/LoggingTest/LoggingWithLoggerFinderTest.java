/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8172971
 * @summary Smoke test to check that logging in java.management is performed
 *          through System.Logger. This test installs a LoggerFinder service
 *          provider and verifies that it gets the traces.
 * @author danielfuchs
 *
 * @build test.loggerfinder/test.loggerfinder.TestLoggerFinder LoggingTest LoggingWithLoggerFinderTest
 * @run main/othervm --add-modules test.loggerfinder LoggingWithLoggerFinderTest
 */
public class LoggingWithLoggerFinderTest {

    public static void main(String[] args) {
        // Replace System.err
        LoggingTest.TestStream ts = new LoggingTest.TestStream(System.err);
        System.setErr(ts);

        // run the test
        new LoggingTest().run(ts);
    }

}

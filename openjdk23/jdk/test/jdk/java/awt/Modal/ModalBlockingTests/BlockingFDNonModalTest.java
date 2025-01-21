/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether a non-modal Dialog created with a Frame
 *          constructor receives focus, whether its components receive focus
 *          and respond to key events. Check also the correctness
 *          of the blocking behavior for the parent Frame.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main BlockingFDNonModalTest
 */

public class BlockingFDNonModalTest {

    public static void main(String[] args) throws Exception {
        (new BlockingFDTest(null)).doTest();
    }
}

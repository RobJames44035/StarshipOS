/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8054143 8163583
 * @summary Check if toBack method works correctly for a non-modal dialog
 *          having a visible Dialog constructor.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main ToBackNonModal6Test
 */

public class ToBackNonModal6Test {

    public static void main(String[] args) throws Exception {
        (new ToBackDDFTest(false)).doTest();
    }
}

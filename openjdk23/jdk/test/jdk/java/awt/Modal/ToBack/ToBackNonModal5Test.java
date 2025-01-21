/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8054143 8163583
 * @summary Check if toBack method works correctly for a non-modal dialog
 *          having a visible Frame constructor.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main ToBackNonModal5Test
 */

public class ToBackNonModal5Test {

    public static void main(String[] args) throws Exception {
        (new ToBackFDFTest(
            false, ToBackFDFTest.DialogOwner.FRAME)).doTest();
    }
}

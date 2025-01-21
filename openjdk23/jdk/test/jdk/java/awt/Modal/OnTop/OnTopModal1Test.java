/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a modal Dialog created with null Frame
 *          constructor stays on top of the windows it blocks.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopModal1Test
 */

public class OnTopModal1Test {

    public static void main(String[] args) throws Exception {
        (new OnTopFDFTest(
            OnTopFDFTest.DialogOwner.NULL_FRAME)).doTest();
    }
}

/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a modal Dialog created with visible Dialog
 *          constructor stays on top of the windows it blocks.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopModal6Test
 */

public class OnTopModal6Test {

    public static void main(String[] args) throws Exception {
        (new OnTopDDFTest()).doTest();
    }
}

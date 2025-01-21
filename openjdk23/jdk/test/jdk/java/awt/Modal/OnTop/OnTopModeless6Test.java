/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a modeless Dialog created with a visible Dialog
 *          constructor follows a normal Z order.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopModeless6Test
 */

public class OnTopModeless6Test {

    public static void main(String[] args) throws Exception {
        (new OnTopDDFTest(Dialog.ModalityType.MODELESS)).doTest();
    }
}

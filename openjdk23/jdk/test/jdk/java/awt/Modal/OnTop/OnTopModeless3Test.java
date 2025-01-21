/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a modeless Dialog created with a
 *          hidden Frame constructor follows normal Z Order.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopModeless3Test
 */

public class OnTopModeless3Test {

    public static void main(String[] args) throws Exception {
        (new OnTopFDFTest(
            Dialog.ModalityType.MODELESS,
            OnTopFDFTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}

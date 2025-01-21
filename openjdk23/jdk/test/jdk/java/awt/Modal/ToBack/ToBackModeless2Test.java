/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054143
 * @summary Check whether a modeless dialog having a null Dialog constructor
 *          goes behind other windows when toBack is called for it.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main ToBackModeless2Test
 */

public class ToBackModeless2Test {

    public static void main(String[] args) throws Exception {
        (new ToBackFDFTest(Dialog.ModalityType.MODELESS,
            ToBackFDFTest.DialogOwner.NULL_DIALOG)).doTest();
    }
}

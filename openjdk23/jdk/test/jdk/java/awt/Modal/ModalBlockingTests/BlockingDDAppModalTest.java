/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether an application modal Dialog created with a Dialog
 *          constructor receives focus, whether its components receive focus
 *          and respond to key events. Check also the correctness
 *          of blocking behavior for the parent Dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @run main BlockingDDAppModalTest
 */

public class BlockingDDAppModalTest {

    public static void main(String[] args) throws Exception {
        (new BlockingDDTest(Dialog.ModalityType.APPLICATION_MODAL)).doTest();
    }
}

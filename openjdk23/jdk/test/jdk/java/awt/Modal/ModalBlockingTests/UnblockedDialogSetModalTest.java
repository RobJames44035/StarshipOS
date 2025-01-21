/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether a modal Dialog receives focus; check
 *          if its components receive focus and respond to key events
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @run main UnblockedDialogSetModalTest
 */

public class UnblockedDialogSetModalTest {

    public static void main(String[] args) throws Exception {
        (new UnblockedDialogTest()).doTest();
    }
}

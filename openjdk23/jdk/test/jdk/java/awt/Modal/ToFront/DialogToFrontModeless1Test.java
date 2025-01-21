/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8050885
 * @summary Check that calling toFront method does not bring a dialog to the top
 *          of a child modeless dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main DialogToFrontModeless1Test
 */

public class DialogToFrontModeless1Test {

    public static void main(String[] args) throws Exception {
        (new DialogToFrontModelessTest()).doTest();
    }
}

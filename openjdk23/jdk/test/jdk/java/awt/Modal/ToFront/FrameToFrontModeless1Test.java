/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8050885
 * @summary Check that calling toFront method does not bring a frame to the top of
 *          a modeless child dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main FrameToFrontModeless1Test
 */

public class FrameToFrontModeless1Test {

    public static void main(String[] args) throws Exception {
        (new FrameToFrontModelessTest(true)).doTest();
    }
}

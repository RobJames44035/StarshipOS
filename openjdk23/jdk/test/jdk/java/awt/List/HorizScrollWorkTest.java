/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6355467
 * @summary Horizontal scroll bar thumb of a List does not stay at the end
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @requires (os.family == "linux")
 * @run main/manual HorizScrollWorkTest
*/

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.List;

public class HorizScrollWorkTest {

    private static final String INSTRUCTIONS = """
            This is a linux only test.
            Drag and drop the horizontal scroll bar thumb at the right end.
            If the thumb does not stay at the right end, then the test failed. Otherwise passed.""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("HorizScrollWorkTest Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int)INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(HorizScrollWorkTest::createTestUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createTestUI() {
        Frame frame = new Frame("HorizScrollWorkTest Frame");
        List list = new List(4);

        frame.setLayout (new FlowLayout());

        list.add("veryyyyyyyyyyyyyyyyyyyyyyyyyy longgggggggggggggggggggggg stringggggggggggggggggggggg");

        frame.add(list);
        frame.pack();

        return frame;
    }
}

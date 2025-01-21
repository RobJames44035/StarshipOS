/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;

/*
 * @test
 * @bug 4410522
 * @requires (os.family == "windows")
 * @summary The box size of the Checkbox control should be the same as
 *          in Windows native applications.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual CheckboxBoxSizeTest
 */

public class CheckboxBoxSizeTest {
    private static final String INSTRUCTIONS = """
            This test must be run at UI Scale of 100% AND
            150% or greater.
            Compare the size of box to any of native apps on Windows
            (Eg. Font Dialog Settings on Word).
            They should be the same.

            If the sizes are same Press PASS, else Press FAIL.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                      .title("CheckboxBoxSizeTest Instructions")
                      .instructions(INSTRUCTIONS)
                      .rows((int) INSTRUCTIONS.lines().count() + 2)
                      .columns(35)
                      .testUI(CheckboxBoxSizeTest::createTestUI)
                      .build()
                      .awaitAndCheck();
    }

    private static Frame createTestUI() {
        Frame frame = new Frame("CheckboxBoxSizeTest");
        Panel panel = new Panel(new FlowLayout());
        Checkbox checkbox = new Checkbox("Compare the box size");
        panel.add(checkbox);
        frame.add(panel);
        frame.pack();
        return frame;
    }
}

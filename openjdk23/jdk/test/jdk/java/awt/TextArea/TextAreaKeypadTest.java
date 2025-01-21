/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;

/*
 * @test
 * @bug 6240876
 * @summary Number pad up & down arrows don't work in XToolkit TextArea
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual TextAreaKeypadTest
 */

public class TextAreaKeypadTest {
    private static final String INSTRUCTIONS = """
            Press pass if you can move the caret in the textarea with _number pad_ UP/DOWN keys.
            Press fail if you don't.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("TextAreaKeypadTest")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(TextAreaKeypadTest::createGUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createGUI() {
        Frame frame = new Frame("TextAreaKeypadTest");
        frame.setLayout(new BorderLayout());
        TextArea area = new TextArea("One\nTwo\nThree", 3, 3, TextArea.SCROLLBARS_NONE);
        frame.add("Center", area);
        frame.pack();
        return frame;
    }
}

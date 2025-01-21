/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.TextArea;

/*
 * @test
 * @bug 4776535
 * @summary Regression: line should not wrap around into multi lines in TextArea.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual TextAreaLineScrollWrapTest
 */

public class TextAreaLineScrollWrapTest {
    private static final String INSTRUCTIONS = """
            You should see a frame "TextAreaLineScrollWrapTest" with
            a TextArea that contains a very long line.
            If the line is wrapped the test is failed.

            Insert a lot of text lines and move a caret to the last one.
            If a caret hides and a content of the TextArea
            does not scroll the test is failed
            else the test is passed.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("TextAreaLineScrollWrapTest")
                .instructions(INSTRUCTIONS)
                .columns(40)
                .testUI(TextAreaLineScrollWrapTest::createGUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createGUI() {
        Frame f = new Frame("TextAreaLineScrollWrapTest");
        f.add(new TextArea("long long long long long long long line...........",
                3, 4));
        f.setSize(100, 100);
        return f;
    }
}

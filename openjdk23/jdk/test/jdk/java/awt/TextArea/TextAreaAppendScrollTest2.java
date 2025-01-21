/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;

/*
 * @test
 * @bug 6192116
 * @summary Auto-scrolling does not work properly for TextArea when appending some text, on XToolkit
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual TextAreaAppendScrollTest2
 */

public class TextAreaAppendScrollTest2 extends Frame {
    TextArea area;
    private static final String INSTRUCTIONS = """
            Press pass if you see exclamation marks in the bottom of textarea.
            Press fail if you don't.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("TextAreaAppendScrollTest2")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(TextAreaAppendScrollTest2::new)
                .build()
                .awaitAndCheck();
    }

    public TextAreaAppendScrollTest2() {
        setLayout(new BorderLayout());
        area = new TextArea("AWT is cool ", 3, 3, TextArea.SCROLLBARS_NONE);
        add("Center", area);
        setSize(200, 200);
        StringBuilder coolStr = new StringBuilder("");
        // I count 15 lines with 12 cools per line
        for (int i = 0; i < 12 * 15; i++) {
            coolStr.append("cool ");
        }
        coolStr.append("!!!!!!!");
        area.append(coolStr.toString());
    }
}

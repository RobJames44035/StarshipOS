/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;

/*
 * @test
 * @bug 4341196
 * @summary Tests that TextArea can handle more than 64K of text
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual TextAreaLimit
 */

public class TextAreaLimit extends Frame {
    static TextArea text;
    private static final String INSTRUCTIONS = """
            You will see a text area with 40000 lines of text
            each with its own line number. If you see the caret after line 39999
            then test passes. Otherwise it fails.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("TextAreaLimit")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(TextAreaLimit::new)
                .build()
                .awaitAndCheck();
    }

    public TextAreaLimit() {
        setLayout(new BorderLayout());

        text = new TextArea();
        add(text);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 40000; i++) {
            buf.append(i + "\n");
        }
        text.setText(buf.toString());
        text.setCaretPosition(buf.length());
        text.requestFocus();
        setSize(200, 200);
    }
}

/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 5003402
 * @summary TextArea must scroll automatically when calling append and select, even when not in focus
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual TextAreaAppendScrollTest
 */

public class TextAreaAppendScrollTest extends Frame implements ActionListener {
    int phase;
    int pos1, pos2;
    TextArea area;
    private static final String INSTRUCTIONS = """
                Press "Click Here" button.
                The word "First" should be visible in the TextArea.

                Press "Click Here" button again.
                The word "Next" should be visible in the TextArea.

                Press "Click Here" button again.
                The word "Last" should be visible in the TextArea.
                If you have seen all three words, press Pass, else press Fail.
                """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("TextAreaAppendScrollTest")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(TextAreaAppendScrollTest::new)
                .build()
                .awaitAndCheck();
    }

    public TextAreaAppendScrollTest() {
        area = new TextArea();
        add("Center", area);
        Button bt1 = new Button("Click Here");
        add("South", bt1);
        String filler = "";
        for (int i = 0; i < 100; i++) {
            filler = filler + i + "\n";
        }
        String text = filler;
        pos1 = text.length();
        text = text + "First\n" + filler;
        pos2 = text.length();
        text = text + "Next\n" + filler;
        area.setText(text);
        phase = 0;
        bt1.addActionListener(this);
        pack();
    }

    public void actionPerformed(ActionEvent ev) {
        if (phase == 0) {
            area.select(pos1, pos1);
            phase = 1;
        } else if (phase == 1) {
            area.select(pos2, pos2);
            phase = 2;
        } else {
            area.append("Last\n");
            phase = 0;
        }
    }
}

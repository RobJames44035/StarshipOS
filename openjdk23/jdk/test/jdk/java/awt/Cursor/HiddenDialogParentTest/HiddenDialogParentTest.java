/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 5079694
 * @summary Test if JDialog respects setCursor
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual HiddenDialogParentTest
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class HiddenDialogParentTest {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                 You can see a label area in the center of JDialog.
                 Verify that the cursor is a hand cursor in this area.
                 If so, press pass, else press fail.
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(HiddenDialogParentTest::createUI)
                .build()
                .awaitAndCheck();
    }

    public static JDialog createUI() {
        JDialog dialog = new JDialog();
        dialog.setTitle("JDialog Cursor Test");
        dialog.setLayout(new BorderLayout());
        JLabel centerLabel = new JLabel("Cursor should be a hand in this " +
                "label area");
        centerLabel.setBorder(new LineBorder(Color.BLACK));
        centerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dialog.add(centerLabel, BorderLayout.CENTER);
        dialog.setSize(300, 200);

        return dialog;
    }
}

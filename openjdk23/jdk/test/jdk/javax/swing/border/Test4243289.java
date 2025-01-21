/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/*
 * @test
 * @bug 4243289
 * @summary Tests that TitledBorder do not draw line through its caption
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual Test4243289
 */

public class Test4243289 {
    public static void main(String[] args) throws Exception {
        String testInstructions = """
                If TitledBorder with title "Panel Title" is overstruck with
                the border line, test fails, otherwise it passes.
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(testInstructions)
                .rows(3)
                .columns(35)
                .splitUI(Test4243289::init)
                .build()
                .awaitAndCheck();
    }

    public static JComponent init() {
        Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Panel Title",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                font);

        JPanel panel = new JPanel();
        panel.setBorder(border);
        panel.setPreferredSize(new Dimension(100, 100));
        Box main = Box.createVerticalBox();
        main.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        main.add(panel);
        return main;
    }
}

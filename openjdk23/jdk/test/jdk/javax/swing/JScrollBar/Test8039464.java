/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * @test
 * @bug 8039464
 * @summary Tests enabling/disabling of titled border's caption
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual Test8039464
 */

public class Test8039464 {
    private static final String INSTRUCTIONS = """
            If the scrollbar thumb is painted correctly in system lookandfeel
            click Pass else click Fail.  """;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            throw new Error("unexpected", exception);
        }
    }

    private static void init(Container container) {
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel label = new JLabel();
        Dimension size = new Dimension(111, 0);
        label.setPreferredSize(size);
        label.setMinimumSize(size);
        container.add(label, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        container.add(new JScrollBar(JScrollBar.HORIZONTAL, 1, 111, 1, 1111), gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        container.add(new JScrollBar(JScrollBar.VERTICAL, 1, 111, 1, 1111), gbc);
    }

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("JScrollBar Instructions")
                .instructions(INSTRUCTIONS)
                .rows(5)
                .columns(35)
                .testUI(Test8039464::createTestUI)
                .build()
                .awaitAndCheck();
    }

    private static JFrame createTestUI() {
        JFrame frame = new JFrame("8039464");
        init(frame);
        frame.pack();
        return frame;
    }
}

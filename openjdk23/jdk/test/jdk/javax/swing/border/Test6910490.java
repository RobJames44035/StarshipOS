/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import static java.awt.Color.RED;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.MatteBorder;

/*
 * @test
 * @bug 6910490
 * @summary Tests a matte border around a component inside a scroll pane.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual Test6910490
 */

public class Test6910490 implements Icon {
    public static void main(String[] args) throws Exception {
        String testInstructions = """
                If the border is painted over scroll bars then test fails.
                Otherwise test passes.""";
        Test6910490 obj = new Test6910490();
        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(testInstructions)
                .rows(3)
                .columns(35)
                .testUI(obj.initializeTest())
                .build()
                .awaitAndCheck();
    }

    public JFrame initializeTest() {
        Insets insets = new Insets(10, 10, 10, 10);
        JFrame frame = new JFrame("Matte Border Test");
        frame.setSize(600, 300);
        Dimension size = new Dimension(frame.getWidth() / 2, frame.getHeight());
        JSplitPane pane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                create("Color", size, new MatteBorder(insets, RED)),
                create("Icon", size, new MatteBorder(insets, this)));

        pane.setDividerLocation(size.width - pane.getDividerSize() / 2);
        frame.add(pane);
        return frame;
    }

    private JScrollPane create(String name, Dimension size, MatteBorder border) {
        JButton button = new JButton(name);
        button.setPreferredSize(size);
        button.setBorder(border);
        return new JScrollPane(button);
    }

    public int getIconWidth() {
        return 10;
    }

    public int getIconHeight() {
        return 10;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(RED);
        g.fillRect(x, y, getIconWidth(), getIconHeight());
    }
}

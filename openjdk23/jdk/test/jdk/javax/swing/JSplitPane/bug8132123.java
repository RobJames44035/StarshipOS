/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/*
 * @test
 * @bug 8132123
 * @summary MultiResolutionCachedImage unnecessarily creates base image
 *          to get its size
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual/othervm -Dsun.java2d.uiScale=2 bug8132123
 */

public class bug8132123 {

    private static final String INSTRUCTIONS = """
            Verify that JSplitPane uses high-resolution system icons for
             the one-touch expanding buttons on HiDPI displays.

            If the display does not support HiDPI mode press PASS.

            1. Run the test on HiDPI Display.
            2. Check that the one-touch expanding buttons on the JSplitPane are painted
            correctly. If so, press PASS, else press FAIL. """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("JSplitPane Instructions")
                .instructions(INSTRUCTIONS)
                .rows(10)
                .columns(40)
                .testUI(bug8132123::init)
                .build()
                .awaitAndCheck();
    }

    public static JFrame init() {
        JFrame frame = new JFrame("Test SplitPane");
        JPanel left = new JPanel();
        left.setBackground(Color.GRAY);
        JPanel right = new JPanel();
        right.setBackground(Color.GRAY);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                left, right);
        splitPane.setOneTouchExpandable(true);
        frame.setLayout(new BorderLayout());
        frame.setSize(250, 250);
        frame.getContentPane().add(splitPane);
        return frame;
    }
}

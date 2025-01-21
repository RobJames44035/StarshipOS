/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicToolBarUI;

/*
 * @test
 * @key headful
 * @bug 4529206
 * @summary JToolBar - setFloating does not work correctly
 * @run main bug4529206
 */

public class bug4529206 {
    static JFrame frame;
    static JToolBar jToolBar1;
    static JButton jButton1;

    private static void test() {
        frame = new JFrame("bug4529206");
        JPanel jPanFrame = (JPanel) frame.getContentPane();
        jPanFrame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(200, 100));
        frame.setTitle("Test Floating Toolbar");
        jToolBar1 = new JToolBar();
        jButton1 = new JButton("Float");
        jPanFrame.add(jToolBar1, BorderLayout.NORTH);
        JTextField tf = new JTextField("click here");
        jPanFrame.add(tf);
        jToolBar1.add(jButton1, null);
        jButton1.addActionListener(e -> buttonPressed());

        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void makeToolbarFloat() {
        BasicToolBarUI ui = (BasicToolBarUI) jToolBar1.getUI();
        if (!ui.isFloating()) {
            ui.setFloatingLocation(100, 100);
            ui.setFloating(true, jToolBar1.getLocation());
        }
    }

    private static void buttonPressed() {
        makeToolbarFloat();
    }

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> test());
            Robot robot = new Robot();
            robot.waitForIdle();
            robot.delay(1000);

            SwingUtilities.invokeAndWait(() -> makeToolbarFloat());
            robot.waitForIdle();
            robot.delay(300);

            SwingUtilities.invokeAndWait(() -> {
                if (frame.isFocused()) {
                    throw new RuntimeException(
                        "setFloating does not work correctly");
                }
            });
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}

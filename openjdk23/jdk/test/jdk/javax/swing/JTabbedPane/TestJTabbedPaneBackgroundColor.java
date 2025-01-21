/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * @test
 * @key headful
 * @bug 8007563
 * @summary Tests JTabbedPane background
 */

public class TestJTabbedPaneBackgroundColor {
    private static ArrayList<String> lafList = new ArrayList<>();
    private static JFrame frame;
    private static JTabbedPane pane;
    private static Robot robot;
    private static volatile Dimension dim;
    private static volatile Point loc;

    public static void main(String[] args) throws Exception {
        robot = new Robot();

        for (UIManager.LookAndFeelInfo laf :
                UIManager.getInstalledLookAndFeels()) {
            System.out.println("Testing: " + laf.getName());
            setLookAndFeel(laf);

            try {
                SwingUtilities.invokeAndWait(TestJTabbedPaneBackgroundColor::createAndShowUI);
                robot.waitForIdle();
                robot.delay(500);

                SwingUtilities.invokeAndWait(() -> {
                    loc = pane.getLocationOnScreen();
                    dim = pane.getSize();
                });

                loc = new Point(loc.x + dim.width - 2, loc.y + 2);
                doTesting(loc, laf);

                if (!pane.isOpaque()) {
                    pane.setOpaque(true);
                    pane.repaint();
                }
                robot.waitForIdle();
                robot.delay(500);

                doTesting(loc, laf);

            } finally {
                SwingUtilities.invokeAndWait(() -> {
                    if (frame != null) {
                        frame.dispose();
                    }
                });
            }
        }
        if (!lafList.isEmpty()) {
            throw new RuntimeException(lafList.toString());
        }
    }

    private static void setLookAndFeel(UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported LAF: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createAndShowUI() {
        pane = new JTabbedPane();
        pane.setOpaque(false);
        pane.setBackground(Color.RED);
        for (int i = 0; i < 3; i++) {
            pane.addTab("Tab " + i, new JLabel("Content area " + i));
        }
        frame = new JFrame("Test Background Color");
        frame.getContentPane().setBackground(Color.BLUE);
        frame.add(pane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void doTesting(Point p, UIManager.LookAndFeelInfo laf) {
        boolean isOpaque = pane.isOpaque();
        Color actual = robot.getPixelColor(p.x, p.y);
        Color expected = isOpaque
                ? pane.getBackground()
                : frame.getContentPane().getBackground();

        if (!expected.equals(actual)) {
            addOpaqueError(laf.getName(), isOpaque);
        }
    }

    private static void addOpaqueError(String lafName, boolean opaque) {
        lafList.add(lafName + " opaque=" + opaque);
    }
}

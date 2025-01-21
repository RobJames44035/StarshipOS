/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4846413
 * @summary Checks if No tooltip modification when no KeyStroke modifier
 * @library ../../regtesthelpers
 * @build Util
 * @author Konstantin Eremin
 * @run main bug4846413
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.plaf.metal.MetalToolTipUI;

public class bug4846413 {

    private static volatile boolean isTooltipAdded;
    private static JButton button;

    public static void main(String[] args) throws Exception {

        Robot robot = new Robot();
        robot.setAutoDelay(50);

        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });

        robot.waitForIdle();

        Point movePoint = getButtonPoint();
        robot.mouseMove(movePoint.x, movePoint.y);
        robot.waitForIdle();

        long timeout = System.currentTimeMillis() + 9000;
        while (!isTooltipAdded && (System.currentTimeMillis() < timeout)) {
            try {Thread.sleep(500);} catch (Exception e) {}
        }

        checkToolTip();
    }

    private static void checkToolTip() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                JToolTip tooltip = (JToolTip) Util.findSubComponent(
                        JFrame.getFrames()[0], "JToolTip");

                if (tooltip == null) {
                    throw new RuntimeException("Tooltip has not been found!");
                }

                MetalToolTipUI tooltipUI = (MetalToolTipUI) MetalToolTipUI.createUI(tooltip);
                tooltipUI.installUI(tooltip);

                if (!"-Insert".equals(tooltipUI.getAcceleratorString())) {
                    throw new RuntimeException("Tooltip acceleration is not properly set!");
                }

            }
        });
    }

    private static Point getButtonPoint() throws Exception {
        final Point[] result = new Point[1];

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                Point p = button.getLocationOnScreen();
                Dimension size = button.getSize();
                result[0] = new Point(p.x + size.width / 2, p.y + size.height / 2);
            }
        });
        return result[0];
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);

        button = new JButton("Press me");
        button.setToolTipText("test");
        button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0, true), "someCommand");
        button.getActionMap().put("someCommand", null);
        frame.getContentPane().add(button);

        JLayeredPane layeredPane = (JLayeredPane) Util.findSubComponent(
                frame, "JLayeredPane");
        layeredPane.addContainerListener(new ContainerAdapter() {

            @Override
            public void componentAdded(ContainerEvent e) {
                isTooltipAdded = true;
            }
        });

        frame.setVisible(true);
    }
}

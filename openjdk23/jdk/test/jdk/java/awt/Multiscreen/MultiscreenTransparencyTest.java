/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * @test
 * @bug 8006421
 * @key headful
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Test to check if Transparency of a frame when dragged is maintained
 * across multiple screens.
 * @run main/manual MultiscreenTransparencyTest
 */

public class MultiscreenTransparencyTest {
    static Point dragStart = new Point();
    static JFrame frame;
    static JPanel panel;
    static PassFailJFrame passFailJFrame;

    public static void main(String[] args) throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();

        if (gds.length < 2) {
            System.out.println("Returning without testing." +
                    "Min 2 Display screens are required for the test!");
            return;
        }
        SwingUtilities.invokeAndWait(() -> {
            try {
                initialize();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        passFailJFrame.awaitAndCheck();
    }

    private static void initialize() throws InterruptedException,
            InvocationTargetException {
        final String INSTRUCTIONS = """
                Instructions to Test:
                1. This test requires multi screen display setup.
                2. Drag the red transparent frame with mouse to second monitor.
                3. If the set transparency is retained then test PASS
                else test is FAIL.
                """;
        panel = new Custom();
        panel.setOpaque(false);
        panel.setBackground(null);
        frame = new JFrame();
        passFailJFrame = new PassFailJFrame("Test Instructions", INSTRUCTIONS, 5L, 8, 40);

        PassFailJFrame.addTestWindow(frame);
        PassFailJFrame.positionTestWindow(frame, PassFailJFrame.Position.HORIZONTAL);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setTitle("Transparency Test");
        frame.getContentPane().add(panel);
        frame.setSize(320, 240);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(1, 1));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.err.println(frame.getGraphicsConfiguration());
            }
        });

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(
                        e.getXOnScreen() - dragStart.x,
                        e.getYOnScreen() - dragStart.y
                );
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    private static class Custom extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(255, 0, 0, 127));
            g.fillOval(0, 0, getWidth(), getHeight());
        }
    }
}

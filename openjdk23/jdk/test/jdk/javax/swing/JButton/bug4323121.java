/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4323121
 * @summary Tests whether a button model always returns true for isArmed()
 *          when mouse hovers over the button
 * @key headful
 * @run main bug4323121
 */

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static java.util.concurrent.TimeUnit.SECONDS;

public final class bug4323121 {

    static JFrame frame;
    static JButton button;

    static volatile Point buttonCenter;

    private static final CountDownLatch mouseEntered = new CountDownLatch(1);

    // Usage of this flag is thread-safe because of using the mouseEntered latch
    private static boolean modelArmed;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);

        try {
            SwingUtilities.invokeAndWait(() -> {
                button = new JButton("gotcha");
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (button.getModel().isArmed()) {
                            modelArmed = true;
                        }
                        mouseEntered.countDown();
                    }
                });

                frame = new JFrame("bug4323121");
                frame.getContentPane().add(button);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });

            robot.waitForIdle();

            SwingUtilities.invokeAndWait(() -> {
                Point location = button.getLocationOnScreen();
                buttonCenter = new Point(location.x + button.getWidth() / 2,
                                         location.y + button.getHeight() / 2);
            });

            robot.mouseMove(buttonCenter.x, buttonCenter.y);

            if (!mouseEntered.await(1, SECONDS)) {
                throw new RuntimeException("Mouse entered event wasn't received");
            }
            if (modelArmed) {
                throw new RuntimeException("getModel().isArmed() returns true "
                                           + "when mouse hovers over the button");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

}

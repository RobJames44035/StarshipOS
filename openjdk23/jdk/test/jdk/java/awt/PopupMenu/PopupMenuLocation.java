/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

/*
 * @test
 * @key headful
 * @bug 8160270
 * @run main/timeout=300 PopupMenuLocation
 */
public final class PopupMenuLocation {

    private static final int SIZE = 350;
    public static final String TEXT =
            "Long-long-long-long-long-long-long text in the item-";
    public static final int OFFSET = 50;
    private static volatile boolean action = false;
    private static Robot robot;
    private static Frame frame;
    private static Rectangle screenBounds;


    public static void main(final String[] args) throws Exception {
        robot = new Robot();
        robot.setAutoDelay(200);
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] sds = ge.getScreenDevices();
        for (GraphicsDevice sd : sds) {
            GraphicsConfiguration gc = sd.getDefaultConfiguration();
            screenBounds = gc.getBounds();
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
            Point point = new Point(screenBounds.x + insets.left,
                                    screenBounds.y + insets.top);
            final int yBound = screenBounds.y + screenBounds.height
                               - insets.bottom - SIZE;
            final int xBound = screenBounds.x + screenBounds.width
                               - insets.right - SIZE;
            while (point.y < yBound) {
                while (point.x < xBound) {
                    test(point);
                    point.translate(screenBounds.width / 5, 0);
                }
                point.setLocation(screenBounds.x,
                                  point.y + screenBounds.height / 5);
            }
        }
    }

    private static void test(final Point loc) {
        frame = new Frame();
        PopupMenu pm = new PopupMenu();
        IntStream.rangeClosed(1, 6).forEach(i -> pm.add(TEXT + i));
        pm.addActionListener(e -> {
            action = true;
            System.out.println(" Got action event " + e);
        });

        try {
            frame.setUndecorated(true);
            frame.setAlwaysOnTop(true);
            frame.setLayout(new FlowLayout());
            frame.add(pm);
            frame.pack();
            frame.setSize(SIZE, SIZE);
            frame.setLocation(loc);
            frame.setVisible(true);

            frame.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    show(e);
                }

                public void mouseReleased(MouseEvent e) {
                    show(e);
                }

                private void show(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        pm.show(frame, 0, 50);
                    }
                }
            });
            openPopup(frame);
        } finally {
            frame.dispose();
        }
    }

    private static void openPopup(final Frame frame) {
        robot.waitForIdle();
        Point pt = frame.getLocationOnScreen();
        int x = pt.x + frame.getWidth() / 2;
        int y = pt.y + OFFSET;
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(200);
        y += OFFSET;
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();
        if (!action) {
            captureScreen();
            throw new RuntimeException(
                    "Failed, didn't receive the PopupMenu ActionEvent on " +
                    "frame= " + frame + ", isFocused = " + frame.isFocused());
        }
        action = false;
    }

    private static void captureScreen() {
        try {
            ImageIO.write(robot.createScreenCapture(screenBounds),
                          "png",
                          new File("screen.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

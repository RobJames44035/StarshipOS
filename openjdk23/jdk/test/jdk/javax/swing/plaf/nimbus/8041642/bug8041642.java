/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8041642 8079450 8140237
 * @summary Incorrect paint of JProgressBar in Nimbus LF
 */

import java.io.File;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class bug8041642 {

    private static JFrame frame;
    private static Point point;
    private static JProgressBar bar;

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    frame = new JFrame();
                    frame.setUndecorated(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setup(frame);
                }
            });
            final Robot robot = new Robot();
            robot.waitForIdle();
            robot.delay(1000);
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    point = bar.getLocationOnScreen();
                }
            });
            Color color = robot.getPixelColor(point.x + 1, point.y + 7);
            System.out.println("point " + point + " color " + color);
            if (color.getGreen() < 150 || color.getBlue() > 30 ||
                    color.getRed() > 200) {
                Rectangle bounds = frame.getBounds();
                BufferedImage img = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
                Graphics g = img.getGraphics();
                frame.paint(g);
                g.dispose();
                ImageIO.write(img, "png", new File("bug8041642.png"));
                throw new RuntimeException("Bar padding color should be green");
            }

        } finally {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame.dispose();
                }
            });
        }

        System.out.println("ok");
    }

    static void setup(JFrame frame) {
        bar = new JProgressBar();
        bar.setBackground(Color.WHITE);
        bar.setValue(2);
        frame.getContentPane().add(bar, BorderLayout.NORTH);
        frame.getContentPane().setBackground(Color.GREEN);
        frame.setSize(200, 150);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }
}

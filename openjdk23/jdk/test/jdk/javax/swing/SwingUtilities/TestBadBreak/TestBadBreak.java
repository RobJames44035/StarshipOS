/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * @test
 * @key headful
 * @bug 8015085 8079253
 * @summary Shortening via " ... " is broken for Strings containing a combining
 *          diaeresis.
 * @run main TestBadBreak
 */
public class TestBadBreak {

    static JFrame frame;
    static Robot robot;
    static final String withCombiningDiaeresis =    "123p://.aaaaaaaaaaaaaaaaaaaaaa.123/a\u0308" ;
    static final String withoutCombiningDiaeresis = "123p://.aaaaaaaaaaaaaaaaaaaaaa.123/\u00E4" ;

    public static void main(final String[] args) throws Exception {
        robot = new Robot();
        final BufferedImage bi1 = new BufferedImage(200, 90, TYPE_INT_ARGB);
        final BufferedImage bi2 = new BufferedImage(200, 90, TYPE_INT_ARGB);
        test(withCombiningDiaeresis, bi1);
        test(withoutCombiningDiaeresis, bi2);
        for (int x = 0; x < bi1.getWidth(); ++x) {
            for (int y = 0; y < bi1.getHeight(); ++y) {
                if (bi1.getRGB(x, y) != bi2.getRGB(x, y)) {
                    ImageIO.write(bi1, "png", new File("image1.png"));
                    ImageIO.write(bi2, "png", new File("image2.png"));
                    throw new RuntimeException("Wrong color");
                }
            }
        }
    }

    private static void test(final String text, final BufferedImage i1)
            throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame();
                final JLabel label = new JLabel(text) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2d = i1.createGraphics();
                        super.paintComponent(g2d);
                        g2d.dispose();
                    }
                };
                label.setOpaque(true);
                frame.getContentPane().add(label);
                frame.setBounds(200, 200, 200, 90);
            }
        });
        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> frame.setVisible(true));
        robot.waitForIdle();
        SwingUtilities.invokeAndWait(frame::dispose);
        robot.waitForIdle();
    }
}

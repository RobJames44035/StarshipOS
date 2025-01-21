/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4221201
 * @summary Test where the gradient drawn should remain in sync with the
 *          rotating rectangle.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual JerkyGradient
 */

import java.awt.Color;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class JerkyGradient extends Panel implements Runnable {
    protected static Shape mShape;
    protected static Paint mPaint;
    protected static double mTheta;
    static Thread animatorThread;
    static BufferedImage mImg;

    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                Watch at least one full rotation of the rectangle. Check that
                the gradient drawn remains in sync with the rotating
                rectangle. If so, pass this test. Otherwise, fail this test.
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .columns(35)
                .testUI(JerkyGradient::createUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createUI() {
        Frame f = new Frame("Rotating Gradient Test");
        JerkyGradient jg = new JerkyGradient();
        f.add(jg);
        f.setSize(200, 200);
        return f;
    }

    public JerkyGradient() {
        mShape = new Rectangle2D.Double(60, 50, 80, 100);
        mPaint = new GradientPaint(0, 0, Color.red,
                25, 25, Color.yellow,
                true);
        mImg = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

        animatorThread = new Thread(this);
        animatorThread.setPriority(Thread.MIN_PRIORITY);
        animatorThread.start();
    }

    public synchronized void run() {
        Thread me = Thread.currentThread();
        double increment = Math.PI / 36;
        double twoPI = Math.PI * 2;

        while (animatorThread == me) {
            mTheta = (mTheta + increment) % twoPI;
            repaint();
            try {
                wait(50);
            } catch (InterruptedException ie) {
                break;
            }
        }
    }

    public void update(Graphics g) {
        Graphics2D g2 = mImg.createGraphics();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, 200, 200);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.rotate(mTheta, 100, 100);
        g2.setPaint(Color.black);
        g2.drawLine(100, 30, 100, 55);
        g2.setPaint(mPaint);
        g2.fill(mShape);
        g2.setPaint(Color.black);
        g2.draw(mShape);
        paint(g);
        g2.dispose();
    }

    public void paint(Graphics g) {
        g.drawImage(mImg, 0, 0, null);
    }
}

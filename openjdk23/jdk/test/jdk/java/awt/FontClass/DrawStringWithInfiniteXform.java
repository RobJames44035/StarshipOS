/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
/*
 * @test
 * @bug 8023213
 * @summary Font/Text APIs should not crash/takes long time
 *          if transform includes INFINITY
 * @run main DrawStringWithInfiniteXform
 */
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class DrawStringWithInfiniteXform {

    volatile Timer timer;
    volatile boolean done;

    class ScheduleTask extends TimerTask {
        public void run() {
            System.out.println("Task running at " + System.currentTimeMillis());
            System.out.flush();
            synchronized (DrawStringWithInfiniteXform.class) {
               System.out.println(
                   "Checking done at " + System.currentTimeMillis());
               System.out.flush();
                if (!done) {
                    throw new RuntimeException(
                       "drawString with InfiniteXform transform takes long time");
                }
            }
        }
    }
    public DrawStringWithInfiniteXform() {
        timer = new Timer();
        timer.schedule(new ScheduleTask(), 30000);
    }

    public static void main(String [] args) {
        DrawStringWithInfiniteXform test = new DrawStringWithInfiniteXform();
        test.start();
    }

    private void start() {
        System.out.println("start at " + System.currentTimeMillis());
        System.out.flush();
        float[] vals = new float[6];
        for (int i=0; i<6; i++) {
            vals[i] = Float.POSITIVE_INFINITY;
        }
        AffineTransform nanTX = new AffineTransform(vals);

        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();

        g2d.rotate(Float.POSITIVE_INFINITY);
        Font font = g2d.getFont();
        Font xfiniteFont;
        for (int i=0; i<2000; i++) {
            xfiniteFont = font.deriveFont(Float.POSITIVE_INFINITY);
            g2d.setFont(xfiniteFont);
            g2d.drawString("abc", 20, 20);
        }
        System.out.println("Loop done at " + System.currentTimeMillis());
        System.out.flush();
        synchronized (DrawStringWithInfiniteXform.class) {
            done = true;
            timer.cancel();
        }
        System.out.println("Test passed");
    }
}

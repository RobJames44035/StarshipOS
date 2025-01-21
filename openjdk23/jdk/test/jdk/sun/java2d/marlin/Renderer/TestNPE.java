/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug     6887494
 *
 * @summary Verifies that no NullPointerException is thrown in Pisces Renderer
 *          under certain circumstances.
 *
 * @run     main TestNPE
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class TestNPE {

    private static void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(0, 0, 0, 0);
        g2d.setTransform(
               new AffineTransform(4.0f, 0.0f, 0.0f, 4.0f, -1248.0f, -744.0f));
        g2d.draw(new Line2D.Float(131.21428571428572f, 33.0f,
                                  131.21428571428572f, 201.0f));
    }

    public static void main(String[] args) {
        BufferedImage im = new BufferedImage(100, 100,
                                             BufferedImage.TYPE_INT_ARGB);

        // Trigger exception in main thread.
        Graphics g = im.getGraphics();
        paint(g);
    }
}

/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4516037
 * @summary verify that scaled Polygons honor the transform
 */

import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class ScaledPolyTest {

    public static void main(String[] args) {

        Polygon poly = new Polygon();
        poly.addPoint(20, 10);
        poly.addPoint(30, 30);
        poly.addPoint(10, 30);
        poly.addPoint(20, 10);

        int height = 300;
        int width = 300;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        g2d.translate(10, 10);
        g2d.scale(2, 2);
        g2d.setColor(Color.yellow);
        g2d.fill(poly);
        g2d.setColor(Color.blue);
        g2d.draw(poly);

        /*
         * Examine each row of the image.
         * If the stroked polygon is correctly aligned on the filled polygon,
         * if there is anything except white on the line,
         * the transition will always be white+->blue+->yellow*->blue*->white+
         */
        int bluePix = blue.getRGB();
        int yellowPix = yellow.getRGB();
        int whitePix = white.getRGB();
        for (int y = 0; y < height; y++ ) {
            int x = 0;
            int pix = whitePix;

            while (pix == whitePix && x < width) pix = bi.getRGB(x++, y);
            if (pix == whitePix && x == width) continue; // all white row.

            if (pix != bluePix) throw new RuntimeException("Expected blue");

            while (pix == bluePix) pix = bi.getRGB(x++, y);

            if (pix == yellowPix) {
               while (pix == yellowPix) pix = bi.getRGB(x++, y);
               if (pix != bluePix) throw new RuntimeException("Expected blue");
               while (pix == bluePix) pix = bi.getRGB(x++, y);
               if (pix != whitePix) throw new RuntimeException("Expected white");
            }

            while (pix == whitePix && x < width) pix = bi.getRGB(x++, y);
            if (pix == whitePix && x == width) {
                continue;
            } else {
                throw new RuntimeException("Expected white to finish the row");
            }
        }
    }
}

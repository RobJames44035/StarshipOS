/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8028539
 * @summary Test that drawing a scaled image terminates.
 * @run main/othervm/timeout=60 DrawImageCoordsTest
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DrawImageCoordsTest {

    public static void main(String[] args) {

        /* Create an image to draw, filled in solid red. */
        BufferedImage srcImg =
             new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics srcG = srcImg.createGraphics();
        srcG.setColor(Color.red);
        int w = srcImg.getWidth(null);
        int h = srcImg.getHeight(null);
        srcG.fillRect(0, 0, w, h);

        /* Create a destination image */
        BufferedImage dstImage =
           new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D dstG = dstImage.createGraphics();
        /* draw image under a scaling transform that overflows int */
        AffineTransform tx = new AffineTransform(0.5, 0, 0, 0.5,
                                                  0, 5.8658460197478485E9);
        dstG.setTransform(tx);
        dstG.drawImage(srcImg, 0, 0, null );
        /* draw image under the same overflowing transform, cancelling
         * out the 0.5 scale on the graphics
         */
        dstG.drawImage(srcImg, 0, 0, 2*w, 2*h, null);
        if (Color.red.getRGB() == dstImage.getRGB(w/2, h/2)) {
             throw new RuntimeException("Unexpected color: clipping failed.");
        }
        System.out.println("Test Thread Completed");
    }
}

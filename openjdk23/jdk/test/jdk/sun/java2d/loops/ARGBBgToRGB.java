/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4238978
 * @summary This test verifies that the correct blitting loop is being used.
 *          The correct output should have a yellow border on the top and
 *          left sides of a red box.  The incorrect output would have only
 *          a red box -- no yellow border."
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ARGBBgToRGB {

    public static void main(String[] argv) {
        BufferedImage bi = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bi.createGraphics();
        big.setColor(Color.red);
        big.fillRect(30, 30, 150, 150);

        BufferedImage bi2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D big2 = bi2.createGraphics();
        big2.drawImage(bi, 0, 0, Color.yellow, null);

        int expectYellowPix = bi2.getRGB(0, 0);
        int expectRedPix = bi2.getRGB(50, 50);
        if ((expectYellowPix != Color.yellow.getRGB()) ||
            (expectRedPix != Color.red.getRGB()))
        {
           throw new RuntimeException("Unexpected colors " + expectYellowPix + " " + expectRedPix);
        }
    }
}

/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * @test
 * @key headful
 * @bug 8056009
 * @summary tests whether Graphics.setColor-calls with Color.white are ignored directly
 *          after pipeline initialization for a certain set of operations.
 * @author ceisserer
 */

public class WhiteTextColorTest extends Frame {
    public static volatile boolean success = false;

    public WhiteTextColorTest() {
        Image dstImg = getGraphicsConfiguration()
                .createCompatibleVolatileImage(30, 20);
        Graphics g = dstImg.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, dstImg.getWidth(null), dstImg.getHeight(null));
        g.setColor(Color.WHITE);
        g.drawString("Test", 0, 15);

        BufferedImage readBackImg = new BufferedImage(dstImg.getWidth(null),
                dstImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
        readBackImg.getGraphics().drawImage(dstImg, 0, 0, null);

        for (int x = 0; x < readBackImg.getWidth(); x++) {
            for (int y = 0; y < readBackImg.getHeight(); y++) {
                int pixel = readBackImg.getRGB(x, y);

                // In case a single white pixel is found, the
                // setColor(Color.WHITE)
                // call before was not ignored and the bug is not present
                if (pixel == 0xFFFFFFFF) {
                    return;
                }
            }
        }

        throw new RuntimeException("Test Failed");
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WhiteTextColorTest();
            }
        });
    }
}


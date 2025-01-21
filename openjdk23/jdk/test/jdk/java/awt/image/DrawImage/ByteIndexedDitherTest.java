/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8280964
 * @summary Tests that drawing to a ByteIndexed image dithers correctly.
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ByteIndexedDitherTest {

    public static void main(String[] args) {
        BufferedImage bgr = createBGRImage();
        BufferedImage indexed = createIndexedImage(bgr);
        checkImage(indexed);
    }

    static BufferedImage createBGRImage() {

        int sz = 8;
        BufferedImage img;
        img = new BufferedImage(sz, sz, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = img.createGraphics();
        Color c = new Color(0, 0, 254);
        g.setColor(c);
        g.fillRect(0, 0, sz, sz);
        g.dispose();

        return img;
    }

    static BufferedImage createIndexedImage(BufferedImage srcImage) {

        int w = srcImage.getWidth(null);
        int h = srcImage.getHeight(null);
        BufferedImage
        indexedImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g = indexedImg.createGraphics();
        g.drawImage(srcImage, 0, 0, w, h, null);
        g.dispose();
        return indexedImg;
    }

     static void checkImage(BufferedImage image) {
         int wid = image.getWidth();
         int hgt = image.getHeight();
         for (int y=0; y<hgt; y++) {
             for (int x=0; x<wid; x++) {
                 int v = image.getRGB(x, y);
                 if ((v & 0x00ffff00) != 0) {
                     System.err.println("("+x+","+y+") = " +
                          Integer.toHexString(v));
                     throw new RuntimeException("Unexpected Red or Green");
                 }
             }
         }
    }
}


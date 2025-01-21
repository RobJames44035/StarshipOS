/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @test
 * @bug 8072682
 * @summary Graphics.getDeviceConfiguration().getBounds returns wrong width/height
 * @run main DeviceBounds
 */
public class DeviceBounds {
    public static void main(String[] args) {
        // NB: all images have the same type
        BufferedImage[] images = new BufferedImage[] {
                new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR)
        };
        int count = 0;
        for (BufferedImage i : images) {
            Graphics2D g = i.createGraphics();
            Rectangle bounds[] = new Rectangle[images.length];
            bounds[count] = g.getDeviceConfiguration().getBounds();
            System.out.println(bounds[count]);

            g.dispose();
            if (bounds[count].width != Integer.MAX_VALUE) {
                throw new RuntimeException("Wrong getBounds");
            }
            count++;
        }
    }
}

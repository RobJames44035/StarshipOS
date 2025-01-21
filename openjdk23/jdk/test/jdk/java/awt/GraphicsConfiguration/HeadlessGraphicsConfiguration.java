/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/*
 * @test
 * @summary Check that GraphicsConfiguration methods do not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessGraphicsConfiguration
 */

public class HeadlessGraphicsConfiguration {
    public static void main(String args[]) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Graphics2D gd = ge.createGraphics(new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));
        GraphicsConfiguration gc = gd.getDeviceConfiguration();
        GraphicsDevice gdev = gc.getDevice();
        BufferedImage bi = gc.createCompatibleImage(100, 100);
        bi = gc.createCompatibleImage(100, 100, Transparency.TRANSLUCENT);

        ColorModel cm = gc.getColorModel();
        cm = gc.getColorModel(Transparency.TRANSLUCENT);

        AffineTransform at = gc.getDefaultTransform();
        at = gc.getNormalizingTransform();

        Rectangle r = gc.getBounds();
    }
}

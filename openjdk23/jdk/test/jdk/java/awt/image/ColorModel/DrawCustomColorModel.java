/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.AlphaComposite;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;

/**
 * @test
 * @bug 8275843
 * @key headful
 * @summary No exception or errors should occur.
 */
public final class DrawCustomColorModel {

    public static void main(String[] args) {
        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gd : ge.getScreenDevices()) {
            GraphicsConfiguration[] gcs = gd.getConfigurations();
            for (GraphicsConfiguration gc : gcs) {
                test(gc);
            }
        }
    }

    private static void test(GraphicsConfiguration gc) {
        Frame frame = new Frame(gc);
        frame.setUndecorated(true);
        frame.pack();
        frame.setSize(15, 15);
        ColorModel cm = new DirectColorModel(32,
                                             0xff000000, // Red
                                             0x00ff0000, // Green
                                             0x0000ff00, // Blue
                                             0x000000FF  // Alpha
        );
        WritableRaster wr = cm.createCompatibleWritableRaster(16, 16);
        DataBufferInt buff = (DataBufferInt) wr.getDataBuffer();
        int[] data = buff.getData();
        Arrays.fill(data, -1); // more chance to reproduce
        Image image =  new BufferedImage(cm, wr, false, null);

        Graphics2D graphics = (Graphics2D) frame.getGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        frame.dispose();
    }
}

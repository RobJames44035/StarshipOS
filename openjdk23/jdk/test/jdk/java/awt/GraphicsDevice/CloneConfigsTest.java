/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug     6822057 7124400 8059848 8198613
 *
 * @summary Test verifies that list of supported graphics configurations
 *          can not be changed via modification of elements of an array
 *          returned by getConfiguration() method.
 *
 * @run     main CloneConfigsTest
 * @run     main/othervm -Dsun.java2d.d3d=true CloneConfigsTest
 * @run     main/othervm -Dsun.java2d.noddraw=true CloneConfigsTest
 */

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class CloneConfigsTest {

    public static void main(String[] args) {
        GraphicsEnvironment env =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] devices = env.getScreenDevices();

        GraphicsConfiguration c = new TestConfig();

        for (GraphicsDevice gd : devices) {
            System.out.println("Device: " + gd);

            GraphicsConfiguration[] configs = gd.getConfigurations();

            for (int i = 0; i < configs.length; i++) {
                GraphicsConfiguration gc  = configs[i];
                System.out.println("\tConfig: " + gc);

                configs[i] = c;
            }

            // verify whether array of configs was modified
            configs = gd.getConfigurations();
            for (GraphicsConfiguration gc : configs) {
                if (gc == c) {
                    throw new RuntimeException("Test failed.");
                }
            }
            System.out.println("Test passed.");
        }
    }

    private static class TestConfig extends GraphicsConfiguration {

        @Override
        public GraphicsDevice getDevice() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BufferedImage createCompatibleImage(int width, int height) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ColorModel getColorModel() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ColorModel getColorModel(int transparency) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AffineTransform getDefaultTransform() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AffineTransform getNormalizingTransform() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Rectangle getBounds() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}

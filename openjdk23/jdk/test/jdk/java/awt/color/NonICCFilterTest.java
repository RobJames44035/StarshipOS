/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;

/*
 * @test
 * @bug 8316497
 * @summary Verifies Color filter on non-ICC profile
 */
public final class NonICCFilterTest {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    private enum ColorSpaceSelector {
        GRAY,
        RGB,
        PYCC,
        WRAPPED_GRAY,
        WRAPPED_RGB,
        WRAPPED_PYCC
    }

    private static final class TestColorSpace extends ColorSpace {

        private final ColorSpace cs;

        TestColorSpace(ColorSpace cs) {
            super(cs.getType(), cs.getNumComponents());
            this.cs = cs;
        }

        @Override
        public float[] toRGB(float[] colorvalue) {
            return cs.toRGB(colorvalue);
        }

        @Override
        public float[] fromRGB(float[] rgbvalue) {
            return cs.fromRGB(rgbvalue);
        }

        @Override
        public float[] toCIEXYZ(float[] colorvalue) {
            return cs.toCIEXYZ(colorvalue);
        }

        @Override
        public float[] fromCIEXYZ(float[] xyzvalue) {
            return cs.fromCIEXYZ(xyzvalue);
        }
    }

    private static BufferedImage createTestImage(final ColorSpace cs) {
        ComponentColorModel cm = new ComponentColorModel(cs, false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        WritableRaster raster = cm.createCompatibleWritableRaster(WIDTH, HEIGHT);
        BufferedImage img = new BufferedImage(cm, raster, false, null);

        Graphics2D g = img.createGraphics();
        GradientPaint gp = new GradientPaint(0, 0, Color.GREEN,
                raster.getWidth(), raster.getHeight(), Color.BLUE);
        g.setPaint(gp);
        g.fillRect(0, 0, raster.getWidth(), raster.getHeight());
        g.dispose();

        return img;
    }

    private static ColorSpace createCS(ColorSpaceSelector selector) {
        return switch (selector) {
            case GRAY -> ColorSpace.getInstance(ColorSpace.CS_GRAY);
            case WRAPPED_GRAY -> new TestColorSpace(ColorSpace.getInstance(ColorSpace.CS_GRAY));

            case RGB -> ColorSpace.getInstance(ColorSpace.CS_sRGB);
            case WRAPPED_RGB -> new TestColorSpace(ColorSpace.getInstance(ColorSpace.CS_sRGB));

            case PYCC -> ColorSpace.getInstance(ColorSpace.CS_PYCC);
            case WRAPPED_PYCC -> new TestColorSpace(ColorSpace.getInstance(ColorSpace.CS_PYCC));
        };
    }

    private static boolean areImagesEqual(BufferedImage destTest, BufferedImage destGold) {
        for (int x = 0; x < destTest.getWidth(); x++) {
            for (int y = 0; y < destTest.getHeight(); y++) {
                int rgb1 = destTest.getRGB(x, y);
                int rgb2 = destGold.getRGB(x, y);
                if (rgb1 != rgb2) {
                    System.err.println("x = " + x + ", y = " + y);
                    System.err.println("rgb1 = " + Integer.toHexString(rgb1));
                    System.err.println("rgb2 = " + Integer.toHexString(rgb2));
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BufferedImage srcTest = createTestImage(createCS(ColorSpaceSelector.WRAPPED_GRAY));
        BufferedImage destTest = createTestImage(createCS(ColorSpaceSelector.WRAPPED_RGB));

        BufferedImage srcGold = createTestImage(createCS(ColorSpaceSelector.GRAY));
        BufferedImage destGold = createTestImage(createCS(ColorSpaceSelector.RGB));

        ColorConvertOp gold = new ColorConvertOp(createCS(ColorSpaceSelector.PYCC), null);
        gold.filter(srcTest, destTest);
        gold.filter(srcGold, destGold);

        if (!areImagesEqual(destTest, destGold)) {
            throw new RuntimeException("ICC test failed");
        }

        ColorConvertOp test = new ColorConvertOp(createCS(ColorSpaceSelector.WRAPPED_PYCC), null);
        test.filter(srcTest, destTest);
        test.filter(srcGold, destGold);

        if (!areImagesEqual(destTest, destGold)) {
            throw new RuntimeException("Wrapper test failed");
        }
    }
}

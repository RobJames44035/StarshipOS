/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BaseMultiResolutionImage;
import static java.awt.RenderingHints.KEY_RESOLUTION_VARIANT;
import static java.awt.RenderingHints.VALUE_RESOLUTION_VARIANT_BASE;
import static java.awt.RenderingHints.VALUE_RESOLUTION_VARIANT_DPI_FIT;
import static java.awt.RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT;
import static java.awt.RenderingHints.VALUE_RESOLUTION_VARIANT_DEFAULT;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import sun.java2d.StateTrackable;
import sun.java2d.SunGraphics2D;
import sun.java2d.SurfaceData;
import sun.java2d.loops.SurfaceType;

/**
 * @test
 * @bug 8029339
 * @author Alexander Scherbatiy
 * @summary Custom MultiResolution image support on HiDPI displays
 * @modules java.desktop/sun.java2d
 * @modules java.desktop/sun.java2d.loops
 * @run main MultiResolutionRenderingHintsTest
 */
public class MultiResolutionRenderingHintsTest {

    private static final int BASE_SIZE = 200;
    private static final Color[] COLORS = {
        Color.CYAN, Color.GREEN, Color.BLUE, Color.ORANGE, Color.RED, Color.PINK
    };

    public static void main(String[] args) throws Exception {

        int length = COLORS.length;
        BufferedImage[] resolutionVariants = new BufferedImage[length];
        for (int i = 0; i < length; i++) {
            resolutionVariants[i] = createRVImage(getSize(i), COLORS[i]);
        }

        BaseMultiResolutionImage mrImage = new BaseMultiResolutionImage(
                resolutionVariants);

        // base
        Color color = getImageColor(VALUE_RESOLUTION_VARIANT_BASE, mrImage, 2, 3);
        if (!getColorForScale(1).equals(color)) {
            throw new RuntimeException("Wrong base resolution variant!");
        }

        // dpi fit
        color = getImageColor(VALUE_RESOLUTION_VARIANT_DPI_FIT, mrImage, 2, 3);
        if (!getColorForScale(2).equals(color)) {
            throw new RuntimeException("Resolution variant is not based on dpi!");
        }

        // size fit
        color = getImageColor(VALUE_RESOLUTION_VARIANT_SIZE_FIT, mrImage, 2, 3);
        if (!getColorForScale(6).equals(color)) {
            throw new RuntimeException("Resolution variant is not based on"
                    + " rendered size!");
        }

        // default
        // depends on the policies of the platform
        // just check that exception is not thrown
        getImageColor(VALUE_RESOLUTION_VARIANT_DEFAULT, mrImage, 2, 3);
    }

    private static Color getColorForScale(int scale) {
        return COLORS[scale - 1];
    }

    private static Color getImageColor(final Object renderingHint, Image image,
            double configScale, double graphicsScale) {

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        TestSurfaceData surface = new TestSurfaceData(width, height, configScale);
        SunGraphics2D g2d = new SunGraphics2D(surface,
                Color.BLACK, Color.BLACK, null);
        g2d.setRenderingHint(KEY_RESOLUTION_VARIANT, renderingHint);
        g2d.scale(graphicsScale, graphicsScale);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return surface.getColor(width / 2, height / 2);
    }

    private static int getSize(int i) {
        return (i + 1) * BASE_SIZE;
    }

    private static BufferedImage createRVImage(int size, Color color) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, size, size);
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        g.dispose();
        return image;
    }

    static class TestGraphicsConfig extends GraphicsConfiguration {

        private final double scale;

        TestGraphicsConfig(double scale) {
            this.scale = scale;
        }

        @Override
        public GraphicsDevice getDevice() {
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
            return AffineTransform.getScaleInstance(scale, scale);
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

    static class TestSurfaceData extends SurfaceData {

        private final int width;
        private final int height;
        private final GraphicsConfiguration gc;
        private final BufferedImage buffImage;
        private final double scale;

        public TestSurfaceData(int width, int height, double scale) {
            super(StateTrackable.State.DYNAMIC, SurfaceType.Custom, ColorModel.getRGBdefault());
            this.scale = scale;
            gc = new TestGraphicsConfig(scale);
            this.width = (int) Math.ceil(scale * width);
            this.height = (int) Math.ceil(scale * height);
            buffImage = new BufferedImage(this.width, this.height,
                    BufferedImage.TYPE_INT_RGB);
        }

        Color getColor(int x, int y) {
            int sx = (int) Math.ceil(x * scale);
            int sy = (int) Math.ceil(y * scale);
            return new Color(buffImage.getRGB(sx, sy));
        }

        @Override
        public SurfaceData getReplacement() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public GraphicsConfiguration getDeviceConfiguration() {
            return gc;
        }

        @Override
        public Raster getRaster(int x, int y, int w, int h) {
            return buffImage.getRaster();
        }

        @Override
        public Rectangle getBounds() {
            return new Rectangle(0, 0, width, height);
        }

        @Override
        public Object getDestination() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

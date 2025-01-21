/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8299255
 * @summary Verify no round error in Font scaling
 */

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FontScalerRoundTest {
    public static void main(String[] args) {
        final double SCALE = 4096.0;
        final double STEP = 0.0001;
        final double LIMIT = STEP * 100.0;

        BufferedImage img = new BufferedImage(100, 100,
                                    BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        FontRenderContext frc = g2d.getFontRenderContext();

        Font font = new Font(Font.DIALOG, Font.PLAIN, 1);
        float h1 = getScaledHeight(font, frc, SCALE);
        float h2 = getScaledHeight(font, frc, SCALE + STEP);
        float diff = Math.abs(h1 - h2);

        if (diff > LIMIT) {
            throw new RuntimeException("Font metrix had round error " +
                                       h1 + "," + h2);
        }
    }

    private static float getScaledHeight(Font font,
                                         FontRenderContext frc,
                                         double scale) {
        AffineTransform at = new AffineTransform(scale, 0.0, 0.0, scale,
                                                 0.0, 0.0);
        Font transFont = font.deriveFont(at);
        LineMetrics m = transFont.getLineMetrics("0", frc);
        return m.getHeight();
    }
}


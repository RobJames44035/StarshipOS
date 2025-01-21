/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8242004
 * @summary Font/Text APIs should handle a non-invertible transform.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class NonInvertibleTransformTextTest {

    public static void main(String[] args) {

        // Create a non-invertible transform
        AffineTransform at = new AffineTransform(1f, 0.0f, -15, 0.0, -1, -30);

        // Test creating a text layout
        FontRenderContext frc = new FontRenderContext(at, false, false);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
        TextLayout tl = new TextLayout("ABC", font, frc);
        tl.getOutline(new AffineTransform());

        // Test rendering text
        BufferedImage bi = new BufferedImage(100, 100,
                                             BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,100,100);
        g2d.setColor(Color.red);
        tl.draw(g2d, 50, 50); // first the TextLayout created above

        // Now a laid out GlyphVector
        Font f = g2d.getFont();
        char[] chs = { 'A', 'B', 'C' };
        GlyphVector gv = f.layoutGlyphVector(frc, chs, 0, chs.length, 0);
        g2d.drawGlyphVector(gv, 20, 20);

        // Now under the transform, the basic text drawing calls.
        g2d.setTransform(at);
        g2d.drawString("ABC", 20, 20);
        g2d.drawChars(chs, 0, chs.length, 20, 20);
        // And TextLayout again.
        tl.draw(g2d, 50, 50);
    }
}

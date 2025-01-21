/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Fonts;


import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java2d.Surface;


/**
 * Rendering text as an outline shape.
 */
@SuppressWarnings("serial")
public class Outline extends Surface {

    public Outline() {
        setBackground(WHITE);
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {

        FontRenderContext frc = g2.getFontRenderContext();
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, w / 8);
        Font f1 = new Font(Font.SANS_SERIF, Font.ITALIC, w / 8);
        String s = "AttributedString";
        AttributedString as = new AttributedString(s);
        as.addAttribute(TextAttribute.FONT, f, 0, 10);
        as.addAttribute(TextAttribute.FONT, f1, 10, s.length());
        AttributedCharacterIterator aci = as.getIterator();
        TextLayout tl = new TextLayout(aci, frc);
        float sw = (float) tl.getBounds().getWidth();
        float sh = (float) tl.getBounds().getHeight();
        Shape sha = tl.getOutline(AffineTransform.getTranslateInstance(w / 2 - sw
                / 2, h * 0.2 + sh / 2));
        g2.setColor(BLUE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(sha);
        g2.setColor(MAGENTA);
        g2.fill(sha);

        f = new Font(Font.SERIF, Font.BOLD, w / 6);
        tl = new TextLayout("Outline", f, frc);
        sw = (float) tl.getBounds().getWidth();
        sh = (float) tl.getBounds().getHeight();
        sha = tl.getOutline(AffineTransform.getTranslateInstance(w / 2 - sw / 2, h
                * 0.5 + sh / 2));
        g2.setColor(BLACK);
        g2.draw(sha);
        g2.setColor(RED);
        g2.fill(sha);

        f = new Font(Font.SANS_SERIF, Font.ITALIC, w / 8);
        AffineTransform fontAT = new AffineTransform();
        fontAT.shear(-0.2, 0.0);
        Font derivedFont = f.deriveFont(fontAT);
        tl = new TextLayout("Italic-Shear", derivedFont, frc);
        sw = (float) tl.getBounds().getWidth();
        sh = (float) tl.getBounds().getHeight();
        sha = tl.getOutline(AffineTransform.getTranslateInstance(w / 2 - sw / 2, h
                * 0.80f + sh / 2));
        g2.setColor(GREEN);
        g2.draw(sha);
        g2.setColor(BLACK);
        g2.fill(sha);
    }

    public static void main(String[] s) {
        createDemoFrame(new Outline());
    }
}

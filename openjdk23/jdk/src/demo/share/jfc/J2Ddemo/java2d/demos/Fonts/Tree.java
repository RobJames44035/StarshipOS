/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Fonts;


import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java2d.AnimatingSurface;


/**
 * Transformation of characters.
 */
@SuppressWarnings("serial")
public class Tree extends AnimatingSurface {

    private char theC = 'A';
    private Character theT = Character.valueOf(theC);
    private Character theR = Character.valueOf((char) (theC + 1));

    public Tree() {
        setBackground(WHITE);
    }

    @Override
    public void reset(int w, int h) {
    }

    @Override
    public void step(int w, int h) {
        setSleepAmount(4000);
        theT = Character.valueOf(theC = ((char) (theC + 1)));
        theR = Character.valueOf((char) (theC + 1));
        if (theR.compareTo(Character.valueOf('z')) == 0) {
            theC = 'A';
        }
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {
        int mindim = Math.min(w, h);
        AffineTransform at = new AffineTransform();
        at.translate((w - mindim) / 2.0,
                (h - mindim) / 2.0);
        at.scale(mindim, mindim);
        at.translate(0.5, 0.5);
        at.scale(0.3, 0.3);
        at.translate(-(Twidth + Rwidth), FontHeight / 4.0);
        g2.transform(at);
        tree(g2, mindim * 0.3, 0);

    }
    static Font theFont = new Font(Font.SERIF, Font.PLAIN, 1);
    static double Twidth = 0.6;
    static double Rwidth = 0.6;
    static double FontHeight = 0.75;
    static Color[] colors = { BLUE,
        RED.darker(),
        GREEN.darker() };

    public void tree(Graphics2D g2d, double size, int phase) {
        g2d.setColor(colors[phase % 3]);
        new TextLayout(theT.toString(), theFont, g2d.getFontRenderContext()).
                draw(g2d, 0.0f, 0.0f);
        if (size > 10.0) {
            AffineTransform at = new AffineTransform();
            at.setToTranslation(Twidth, -0.1);
            at.scale(0.6, 0.6);
            g2d.transform(at);
            size *= 0.6;
            new TextLayout(theR.toString(), theFont, g2d.getFontRenderContext()).
                    draw(g2d, 0.0f, 0.0f);
            at.setToTranslation(Rwidth + 0.75, 0);
            g2d.transform(at);
            Graphics2D g2dt = (Graphics2D) g2d.create();
            at.setToRotation(-Math.PI / 2.0);
            g2dt.transform(at);
            tree(g2dt, size, phase + 1);
            g2dt.dispose();
            at.setToTranslation(.75, 0);
            at.rotate(-Math.PI / 2.0);
            at.scale(-1.0, 1.0);
            at.translate(-Twidth, 0);
            g2d.transform(at);
            tree(g2d, size, phase);
        }
        g2d.setTransform(new AffineTransform());
    }

    public static void main(String[] argv) {
        createDemoFrame(new Tree());
    }
}

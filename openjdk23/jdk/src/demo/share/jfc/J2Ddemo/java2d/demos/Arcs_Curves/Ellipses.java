/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Arcs_Curves;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java2d.AnimatingSurface;
import static java.awt.Color.*;


/**
 * Ellipse2D 25 animated expanding ellipses.
 */
@SuppressWarnings("serial")
public final class Ellipses extends AnimatingSurface {

    private static Color[] colors = {
        BLUE, CYAN, GREEN, MAGENTA, ORANGE, PINK, RED,
        YELLOW, LIGHT_GRAY, WHITE };
    private Ellipse2D.Float[] ellipses;
    private double[] esize;
    private float[] estroke;
    private double maxSize;

    public Ellipses() {
        setBackground(BLACK);
        ellipses = new Ellipse2D.Float[25];
        esize = new double[ellipses.length];
        estroke = new float[ellipses.length];
        for (int i = 0; i < ellipses.length; i++) {
            ellipses[i] = new Ellipse2D.Float();
            getRandomXY(i, 20 * Math.random(), 200, 200);
        }
    }

    public void getRandomXY(int i, double size, int w, int h) {
        esize[i] = size;
        estroke[i] = 1.0f;
        double x = Math.random() * (w - (maxSize / 2));
        double y = Math.random() * (h - (maxSize / 2));
        ellipses[i].setFrame(x, y, size, size);
    }

    @Override
    public void reset(int w, int h) {
        maxSize = w / 10;
        for (int i = 0; i < ellipses.length; i++) {
            getRandomXY(i, maxSize * Math.random(), w, h);
        }
    }

    @Override
    public void step(int w, int h) {
        for (int i = 0; i < ellipses.length; i++) {
            estroke[i] += 0.025f;
            esize[i]++;
            if (esize[i] > maxSize) {
                getRandomXY(i, 1, w, h);
            } else {
                ellipses[i].setFrame(ellipses[i].getX(), ellipses[i].getY(),
                        esize[i], esize[i]);
            }
        }
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {
        for (int i = 0; i < ellipses.length; i++) {
            g2.setColor(colors[i % colors.length]);
            g2.setStroke(new BasicStroke(estroke[i]));
            g2.draw(ellipses[i]);
        }
    }

    public static void main(String[] argv) {
        createDemoFrame(new Ellipses());
    }
}

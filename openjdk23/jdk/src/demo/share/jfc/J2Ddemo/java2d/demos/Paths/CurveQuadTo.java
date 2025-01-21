/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Paths;


import static java.awt.Color.BLACK;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.WHITE;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java2d.Surface;


/**
 * Cubic & Quad curves implemented through GeneralPath.
 */
@SuppressWarnings("serial")
public class CurveQuadTo extends Surface {

    public CurveQuadTo() {
        setBackground(WHITE);
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {
        GeneralPath p = new GeneralPath(Path2D.WIND_EVEN_ODD);
        p.moveTo(w * .2f, h * .25f);
        p.curveTo(w * .4f, h * .5f, w * .6f, 0.0f, w * .8f, h * .25f);
        p.moveTo(w * .2f, h * .6f);
        p.quadTo(w * .5f, h * 1.0f, w * .8f, h * .6f);
        g2.setColor(LIGHT_GRAY);
        g2.fill(p);
        g2.setColor(BLACK);
        g2.draw(p);
        g2.drawString("curveTo", (int) (w * .2), (int) (h * .25f) - 5);
        g2.drawString("quadTo", (int) (w * .2), (int) (h * .6f) - 5);
    }

    public static void main(String[] s) {
        createDemoFrame(new CurveQuadTo());
    }
}

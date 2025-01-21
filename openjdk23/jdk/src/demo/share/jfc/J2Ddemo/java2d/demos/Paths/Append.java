/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Paths;


import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java2d.Surface;


/**
 * Simple append of rectangle to path with & without the connect.
 */
@SuppressWarnings("serial")
public class Append extends Surface {

    public Append() {
        setBackground(WHITE);
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {
        GeneralPath p = new GeneralPath(Path2D.WIND_NON_ZERO);
        p.moveTo(w * 0.25f, h * 0.2f);
        p.lineTo(w * 0.75f, h * 0.2f);
        p.closePath();
        p.append(new Rectangle2D.Double(w * .4, h * .3, w * .2, h * .1), false);
        g2.setColor(GRAY);
        g2.fill(p);
        g2.setColor(BLACK);
        g2.draw(p);
        g2.drawString("Append rect to path", (int) (w * .25), (int) (h * .2) - 5);

        p.reset();
        p.moveTo(w * 0.25f, h * 0.6f);
        p.lineTo(w * 0.75f, h * 0.6f);
        p.closePath();
        p.append(new Rectangle2D.Double(w * .4, h * .7, w * .2, h * .1), true);
        g2.setColor(GRAY);
        g2.fill(p);
        g2.setColor(BLACK);
        g2.draw(p);
        g2.drawString("Append, connect", (int) (w * .25), (int) (h * .6) - 5);
    }

    public static void main(String[] s) {
        createDemoFrame(new Append());
    }
}

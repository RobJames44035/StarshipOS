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
 * Rectangles filled to illustrate the GenerPath winding rule, determining
 * the interior of a path.
 */
@SuppressWarnings("serial")
public class WindingRule extends Surface {

    public WindingRule() {
        setBackground(WHITE);
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {

        g2.translate(w * .2, h * .2);

        GeneralPath p = new GeneralPath(Path2D.WIND_NON_ZERO);
        p.moveTo(0.0f, 0.0f);
        p.lineTo(w * .5f, 0.0f);
        p.lineTo(w * .5f, h * .2f);
        p.lineTo(0.0f, h * .2f);
        p.closePath();

        p.moveTo(w * .05f, h * .05f);
        p.lineTo(w * .55f, h * .05f);
        p.lineTo(w * .55f, h * .25f);
        p.lineTo(w * .05f, h * .25f);
        p.closePath();

        g2.setColor(LIGHT_GRAY);
        g2.fill(p);
        g2.setColor(BLACK);
        g2.draw(p);
        g2.drawString("NON_ZERO rule", 0, -5);

        g2.translate(0.0f, h * .45);

        p.setWindingRule(Path2D.WIND_EVEN_ODD);
        g2.setColor(LIGHT_GRAY);
        g2.fill(p);
        g2.setColor(BLACK);
        g2.draw(p);
        g2.drawString("EVEN_ODD rule", 0, -5);
    }

    public static void main(String[] s) {
        createDemoFrame(new WindingRule());
    }
}

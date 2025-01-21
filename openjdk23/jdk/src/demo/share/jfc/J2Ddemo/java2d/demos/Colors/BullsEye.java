/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Colors;


import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java2d.Surface;


/**
 * Creating colors with an alpha value.
 */
@SuppressWarnings("serial")
public class BullsEye extends Surface {

    public BullsEye() {
        setBackground(WHITE);
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {

        Color[] reds = { RED.darker(), RED };
        for (int N = 0; N < 18; N++) {
            float i = (N + 2) / 2.0f;
            float x = (5 + i * (w / 2 / 10));
            float y = (5 + i * (h / 2 / 10));
            float ew = (w - 10) - (i * w / 10);
            float eh = (h - 10) - (i * h / 10);
            float alpha = (N == 0) ? 0.1f : 1.0f / (19.0f - N);
            if (N >= 16) {
                g2.setColor(reds[N - 16]);
            } else {
                g2.setColor(new Color(0f, 0f, 0f, alpha));
            }
            g2.fill(new Ellipse2D.Float(x, y, ew, eh));
        }
    }

    public static void main(String[] s) {
        createDemoFrame(new BullsEye());
    }
}

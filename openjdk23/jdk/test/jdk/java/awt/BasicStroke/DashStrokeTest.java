/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;


public class DashStrokeTest {

    public static void main(String[] args) {

        GeneralPath shape = new GeneralPath();
        int[] pointTypes = {0, 0, 1, 1, 0, 1, 1, 0};
        double[] xpoints = {428, 420, 400, 400, 400, 400, 420, 733};
        double[] ypoints = {180, 180, 180, 160, 30, 10, 10, 10};
        shape.moveTo(xpoints[0], ypoints[0]);
        for (int i = 1; i < pointTypes.length; i++) {
            if (pointTypes[i] == 1 && i < pointTypes.length - 1) {
                shape.quadTo(xpoints[i], ypoints[i],
                             xpoints[i + 1], ypoints[i + 1]);
            } else {
                shape.lineTo(xpoints[i], ypoints[i]);
            }
        }

        BufferedImage image = new
            BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        Color color = new Color(124, 0, 124, 255);
        g2.setColor(color);
        Stroke stroke = new BasicStroke(1.0f,
                                        BasicStroke.CAP_BUTT,
                                        BasicStroke.JOIN_BEVEL,
                                        10.0f, new float[] {9, 6}, 0.0f);
        g2.setStroke(stroke);
        g2.draw(shape);
    }
}

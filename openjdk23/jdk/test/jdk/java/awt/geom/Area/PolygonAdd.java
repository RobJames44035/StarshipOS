/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4286894
 * @summary Verifies that a particular case of Polygon addition
 *          correctly calculates the ordering of the Polygon edges.
 */

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class PolygonAdd {
    public static void main(String argv[]) {
        // Original shapes: 4 touching rectangles
        // (area works correctly with them if not transformed)
        Shape b = new Rectangle(0,0,100,10);
        Shape t = new Rectangle(0,90,100,10);
        Shape l = new Rectangle(0,10,10,80);
        Shape r = new Rectangle(90,10,10,80);

        // Create a transform to rotate them by 10 degrees
        AffineTransform M = new AffineTransform();
        M.translate(-50,-50);
        M.scale(3,3);
        M.rotate(Math.toRadians(10));
        M.translate(70,40);

        Area area = new Area();
        area.add(new Area(M.createTransformedShape(b)));
        area.add(new Area(M.createTransformedShape(l)));
        area.add(new Area(M.createTransformedShape(t)));
        area.add(new Area(M.createTransformedShape(r)));

        if (!area.contains(295, 145, 5, 5)) {
            throw new RuntimeException("Area addition failed!");
        }
    }
}

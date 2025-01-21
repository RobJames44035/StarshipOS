/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6396047
 * @summary tests the CubicCurve2D.contains(x, y) method
 */

import java.awt.geom.CubicCurve2D;

public class ContainsPoint {
    public static void main(String args[]) {
        CubicCurve2D curve =
            new CubicCurve2D.Double(100.0, 100.0,
                                    200.0, 100.0,
                                    200.0, 200.0,
                                    100.0, 200.0);
        if (curve.contains(0.0, 100.0)) {
            throw new RuntimeException("contains point clearly "+
                                       "outside of curve");
        }
    }
}

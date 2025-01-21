/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Polygon;

/*
 * @test
 * @summary Check that Polygon constructor does not throw unexpected exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessPolygon
 */

public class HeadlessPolygon {
    public static void main(String args[]) {
        new Polygon();
    }
}

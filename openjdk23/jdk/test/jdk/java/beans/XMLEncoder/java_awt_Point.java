/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4741757 6402062 6471539
 * @summary Tests Point encoding
 * @run main/othervm java_awt_Point
 * @author Sergey Malenkov
 */

import java.awt.Point;

public final class java_awt_Point extends AbstractTest<Point> {
    public static void main(String[] args) {
        new java_awt_Point().test();
    }

    protected Point getObject() {
        return new Point(-5, 5);
    }

    protected Point getAnotherObject() {
        return new Point();
    }
}

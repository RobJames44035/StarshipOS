/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 4358979
 * @summary Tests LinearGradientPaint encoding
 * @run main/othervm java_awt_LinearGradientPaint
 * @author Sergey Malenkov
 */

import java.awt.Color;
import java.awt.LinearGradientPaint;

public final class java_awt_LinearGradientPaint extends AbstractTest<LinearGradientPaint> {
    public static void main(String[] args) {
        new java_awt_LinearGradientPaint().test();
    }

    protected LinearGradientPaint getObject() {
        float[] f = { 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f };
        Color[] c = { Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED };
        return new LinearGradientPaint(f[0], f[1], f[2], f[3], f, c);
    }

    protected LinearGradientPaint getAnotherObject() {
        return null; /* TODO: could not update property
        float[] f = { 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f };
        Color[] c = { Color.RED, Color.GREEN, Color.BLUE, Color.RED, Color.GREEN, Color.BLUE };
        return new LinearGradientPaint(
                new Point2D.Float(f[0], f[1]),
                new Point2D.Float(f[2], f[3]),
                f, c, REFLECT, LINEAR_RGB,
                new AffineTransform(f));*/
    }
}

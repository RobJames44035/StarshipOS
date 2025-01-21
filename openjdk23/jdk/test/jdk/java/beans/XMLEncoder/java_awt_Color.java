/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests Color encoding
 * @run main/othervm java_awt_Color
 * @author Sergey Malenkov
 */

import java.awt.Color;

public final class java_awt_Color extends AbstractTest<Color> {
    public static void main(String[] args) {
        new java_awt_Color().test();
    }

    protected Color getObject() {
        return new Color(0x88, 0x44, 0x22, 0x11);
    }

    protected Color getAnotherObject() {
        return Color.BLACK;
    }
}

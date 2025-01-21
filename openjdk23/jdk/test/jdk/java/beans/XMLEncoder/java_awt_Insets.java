/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6471539
 * @summary Tests Insets encoding
 * @run main/othervm java_awt_Insets
 * @author Sergey Malenkov
 */

import java.awt.Insets;

public final class java_awt_Insets extends AbstractTest<Insets> {
    public static void main(String[] args) {
        new java_awt_Insets().test();
    }

    protected Insets getObject() {
        return new Insets(1, 2, 3, 4);
    }

    protected Insets getAnotherObject() {
        return new Insets(0, 0, 0, 0);
    }
}

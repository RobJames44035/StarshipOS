/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4741757 6402062 6471539
 * @summary Tests Dimension encoding
 * @run main/othervm java_awt_Dimension
 * @author Sergey Malenkov
 */

import java.awt.Dimension;

public final class java_awt_Dimension extends AbstractTest<Dimension> {
    public static void main(String[] args) {
        new java_awt_Dimension().test();
    }

    protected Dimension getObject() {
        return new Dimension();
    }

    protected Dimension getAnotherObject() {
        return new Dimension(-5, 5);
    }
}

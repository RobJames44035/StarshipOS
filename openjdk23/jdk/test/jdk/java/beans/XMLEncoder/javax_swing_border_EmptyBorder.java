/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests EmptyBorder encoding
 * @run main/othervm javax_swing_border_EmptyBorder
 * @author Sergey Malenkov
 */

import javax.swing.border.EmptyBorder;

public final class javax_swing_border_EmptyBorder extends AbstractTest<EmptyBorder> {
    public static void main(String[] args) {
        new javax_swing_border_EmptyBorder().test();
    }

    protected EmptyBorder getObject() {
        return new EmptyBorder(1, 2, 3, 4);
    }

    protected EmptyBorder getAnotherObject() {
        return null; // TODO: could not update property
        // return new EmptyBorder(4, 3, 2, 1);
    }
}

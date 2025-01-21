/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests CompoundBorder encoding
 * @run main/othervm javax_swing_border_CompoundBorder
 * @author Sergey Malenkov
 */

import javax.swing.border.CompoundBorder;

public final class javax_swing_border_CompoundBorder extends AbstractTest<CompoundBorder> {
    public static void main(String[] args) {
        new javax_swing_border_CompoundBorder().test();
    }

    protected CompoundBorder getObject() {
        return new CompoundBorder(null, new CompoundBorder());
    }

    protected CompoundBorder getAnotherObject() {
        return null; // TODO: could not update property
        // return new CompoundBorder();
    }
}

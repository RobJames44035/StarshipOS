/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062
 * @summary Tests MatteBorder encoding
 * @run main/othervm javax_swing_border_MatteBorder
 * @author Sergey Malenkov
 */

import java.awt.Color;
import javax.swing.border.MatteBorder;

public final class javax_swing_border_MatteBorder extends AbstractTest<MatteBorder> {
    public static void main(String[] args) {
        new javax_swing_border_MatteBorder().test();
    }

    protected MatteBorder getObject() {
        return new MatteBorder(1, 2, 3, 4, Color.RED);
    }

    protected MatteBorder getAnotherObject() {
        return null; // TODO: could not update property
        // return new MatteBorder(4, 3, 2, 1, Color.BLACK);
    }
}

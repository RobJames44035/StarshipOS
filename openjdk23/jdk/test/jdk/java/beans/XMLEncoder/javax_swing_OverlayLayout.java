/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6405175 6487891
 * @summary Tests OverlayLayout encoding
 * @run main/othervm javax_swing_OverlayLayout
 * @author Sergey Malenkov
 */

import javax.swing.JLabel;
import javax.swing.OverlayLayout;

public final class javax_swing_OverlayLayout extends AbstractTest<OverlayLayout> {
    public static void main(String[] args) {
        new javax_swing_OverlayLayout().test();
    }

    protected OverlayLayout getObject() {
        return new OverlayLayout(new JLabel("TEST"));
    }

    protected OverlayLayout getAnotherObject() {
        return null; // TODO: could not update property
        // return new OverlayLayout(new JPanel());
    }
}

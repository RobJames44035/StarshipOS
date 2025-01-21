/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6405175 6487891
 * @summary Tests BoxLayout encoding
 * @run main/othervm javax_swing_BoxLayout
 * @author Sergey Malenkov
 */

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public final class javax_swing_BoxLayout extends AbstractTest<BoxLayout> {
    public static void main(String[] args) {
        new javax_swing_BoxLayout().test();
    }

    protected BoxLayout getObject() {
        return new BoxLayout(new JLabel("TEST"), BoxLayout.LINE_AXIS);
    }

    protected BoxLayout getAnotherObject() {
        return null; // TODO: could not update property
        // return new BoxLayout(new JPanel(), BoxLayout.X_AXIS);
    }
}

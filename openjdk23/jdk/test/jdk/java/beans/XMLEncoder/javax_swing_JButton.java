/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests JButton encoding
 * @run main/othervm javax_swing_JButton
 * @author Sergey Malenkov
 */

import javax.swing.JButton;

public final class javax_swing_JButton extends AbstractTest<JButton> {
    public static void main(String[] args) {
        new javax_swing_JButton().test();
    }

    protected JButton getObject() {
        return new JButton("First");
    }

    protected JButton getAnotherObject() {
        return new JButton("Second");
    }
}

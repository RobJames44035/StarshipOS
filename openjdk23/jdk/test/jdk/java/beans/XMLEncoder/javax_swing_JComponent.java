/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/*
 * @test
 * @bug 8131754
 * @summary Tests JComponent encoding
 * @run main/othervm javax_swing_JComponent
 */
public final class javax_swing_JComponent extends AbstractTest<JComponent> {

    public static void main(final String[] args) {
        new javax_swing_JComponent().test();
    }

    protected JComponent getObject() {
        return new SimpleJComponent();
    }

    protected JComponent getAnotherObject() {
        return new CustomJComponent();
    }

    public static final class SimpleJComponent extends JComponent {

    }

    public static final class CustomJComponent extends JComponent {

        public CustomJComponent() {
            ui = new CustomUI();
        }

        @Override
        public ComponentUI getUI() {
            return ui;
        }

        @Override
        public void setUI(final ComponentUI newUI) {
            ui = newUI;
        }
    }

    public static final class CustomUI extends ComponentUI {

        public boolean getFlag() {
            throw new Error();
        }

        public void setFlag(final boolean flag) {
            throw new Error();
        }
    }
}

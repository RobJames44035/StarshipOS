/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6570354
 * @summary Tests listeners removing
 * @run main/othervm Test6570354
 * @author Sergey Malenkov
 */

import java.beans.PropertyChangeListener;
import javax.swing.JLabel;

public final class Test6570354 extends AbstractTest<JLabel> {
    public static void main(String[] args) {
        new Test6570354().test();
    }

    protected JLabel getObject() {
        JLabel label = new JLabel("");
        label.removePropertyChangeListener((PropertyChangeListener) label.getUI());
        return label;
    }

    protected JLabel getAnotherObject() {
        return new JLabel("");
    }
}

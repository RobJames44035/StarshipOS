/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 4916852
 * @summary Tests BorderLayout encoding
 * @run main/othervm java_awt_BorderLayout
 * @author Sergey Malenkov
 */

import java.awt.BorderLayout;
import javax.swing.JLabel;

public final class java_awt_BorderLayout extends AbstractTest<BorderLayout> {
    private static final String[] CONSTRAINTS = {
            BorderLayout.NORTH,
            BorderLayout.SOUTH,
            BorderLayout.EAST,
            BorderLayout.WEST,
            BorderLayout.CENTER,
            BorderLayout.PAGE_START,
            BorderLayout.PAGE_END,
            BorderLayout.LINE_START,
            BorderLayout.LINE_END,
    };

    public static void main(String[] args) {
        new java_awt_BorderLayout().test();
    }

    @Override
    protected BorderLayout getObject() {
        BorderLayout layout = new BorderLayout();
        update(layout, BorderLayout.EAST);
        update(layout, BorderLayout.WEST);
        update(layout, BorderLayout.NORTH);
        update(layout, BorderLayout.SOUTH);
        return layout;
    }

    @Override
    protected BorderLayout getAnotherObject() {
        BorderLayout layout = getObject();
        update(layout, BorderLayout.CENTER);
        return layout;
    }

    @Override
    protected void validate(BorderLayout before, BorderLayout after) {
        super.validate(before, after);
        for (String constraint : CONSTRAINTS) {
            super.validator.validate(before.getLayoutComponent(constraint),
                                     after.getLayoutComponent(constraint));
        }
    }

    private static void update(BorderLayout layout, String constraints) {
        layout.addLayoutComponent(new JLabel(constraints), constraints);
    }
}

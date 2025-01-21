/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6777487
 * @summary Tests private field access for BoxLayout
 * @run main TestBox
 */

import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;

public final class TestBox {
    private static final Integer OBJECT = Integer.valueOf(-123);

    public static void main(String[] args) {
        TestEncoder.test(
                new Box(BoxLayout.LINE_AXIS),
                new Box(BoxLayout.PAGE_AXIS) {
                    @Override
                    public FlowLayout getLayout() {
                        return new FlowLayout() {
                            private final Object axis = OBJECT;
                        };
                    }
                },
                OBJECT
        );
    }
}

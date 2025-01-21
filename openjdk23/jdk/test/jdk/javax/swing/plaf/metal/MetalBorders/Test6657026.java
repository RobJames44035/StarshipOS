/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6657026
 * @summary Tests constancy of borders
 * @author Sergey Malenkov
 */

import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalBorders.ButtonBorder;
import javax.swing.plaf.metal.MetalBorders.MenuBarBorder;
import javax.swing.plaf.metal.MetalBorders.MenuItemBorder;
import javax.swing.plaf.metal.MetalBorders.PopupMenuBorder;

public class Test6657026 {

    private static final Insets NEGATIVE = new Insets(Integer.MIN_VALUE,
                                                      Integer.MIN_VALUE,
                                                      Integer.MIN_VALUE,
                                                      Integer.MIN_VALUE);

    public static void main(String[] args) {
        new ButtonBorder() {{borderInsets = NEGATIVE;}};
        new MenuBarBorder() {{borderInsets = NEGATIVE;}};
        new MenuItemBorder() {{borderInsets = NEGATIVE;}};
        new PopupMenuBorder() {{borderInsets = NEGATIVE;}};

        test(create("ButtonBorder"));
        test(create("MenuBarBorder"));
        test(create("MenuItemBorder"));
        test(create("PopupMenuBorder"));

        test(create("Flush3DBorder"));
        test(create("InternalFrameBorder"));
        // NOT USED: test(create("FrameBorder"));
        // NOT USED: test(create("DialogBorder"));
        test(create("PaletteBorder"));
        test(create("OptionDialogBorder"));
        test(create("ScrollPaneBorder"));
    }

    private static Border create(String name) {
        try {
            name = "javax.swing.plaf.metal.MetalBorders$" + name;
            return (Border) Class.forName(name).newInstance();
        }
        catch (Exception exception) {
            throw new Error("unexpected exception", exception);
        }
    }

    private static void test(Border border) {
        Insets actual = border.getBorderInsets(null);
        if (NEGATIVE.equals(actual)) {
            throw new Error("unexpected insets in " + border.getClass());
        }
        Insets expected = (Insets) actual.clone();
        // modify
        actual.top++;
        actual.left++;
        actual.right++;
        actual.bottom++;
        // validate
        if (!expected.equals(border.getBorderInsets(null))) {
            throw new Error("shared insets in " + border.getClass());
        }
    }
}

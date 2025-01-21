/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4818598
 * @summary Tests MenuShortcut value encoding
 * @run main/othervm java_awt_MenuShortcut
 * @author Sergey Malenkov
 */

import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

public final class java_awt_MenuShortcut extends AbstractTest<MenuShortcut> {
    public static void main(String[] args) {
        new java_awt_MenuShortcut().test();
    }

    protected MenuShortcut getObject() {
        return new MenuShortcut(KeyEvent.VK_A);
    }

    protected MenuShortcut getAnotherObject() {
        return new MenuShortcut(KeyEvent.VK_Z, true);
    }
}

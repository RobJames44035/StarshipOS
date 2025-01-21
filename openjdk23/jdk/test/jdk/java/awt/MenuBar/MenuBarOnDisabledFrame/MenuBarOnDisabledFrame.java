/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6185057
 * @summary Disabling a frame does not disable the menus on the frame, on
 *      solaris/linux
 * @requires os.family != "mac"
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual MenuBarOnDisabledFrame
 */

import java.awt.Button;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class MenuBarOnDisabledFrame {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                Check if MenuBar is disabled on 'Disabled frame'
                Press pass if menu bar is disabled, fail otherwise
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(MenuBarOnDisabledFrame::createUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createUI() {
        Frame f = new Frame("Disabled frame");
        MenuBar mb = new MenuBar();
        Menu m1 = new Menu("Disabled Menu 1");
        Menu m2 = new Menu("Disabled Menu 2");
        MenuItem m11 = new MenuItem("MenuItem 1.1");
        MenuItem m21 = new MenuItem("MenuItem 2.1");
        Button b = new Button("Disabled button");

        m1.add(m11);
        m2.add(m21);
        mb.add(m1);
        mb.add(m2);
        f.setMenuBar(mb);
        f.add(b);
        f.setEnabled(false);
        f.setSize(300, 300);
        return f;
    }
}

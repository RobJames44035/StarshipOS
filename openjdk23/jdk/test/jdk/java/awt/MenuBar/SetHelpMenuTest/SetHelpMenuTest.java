/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4275843
 * @summary MenuBar doesn't display all of its Menus correctly on Windows
 * @requires os.family == "windows"
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual SetHelpMenuTest
 */

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class SetHelpMenuTest {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                An empty frame should be visible. When focused, the MenuBar
                should have 5 menus ("one", "two", "three", "Help 2",
                "four"). If so, then the test passed. Otherwise, the test
                failed.
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(SetHelpMenuTest::createUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createUI() {
        Frame f = new Frame("Help MenuBar Test");
        f.setSize(100, 100);

        MenuBar mb = new MenuBar();
        Menu h1, h2;

        f.setMenuBar(mb);
        mb.add(createMenu("one", false));
        mb.add(createMenu("two", false));
        mb.add(createMenu("three", true));

        mb.add(h1 = createMenu("Help 1", false));  // h1 is HelpMenu
        mb.setHelpMenu(h1);

        mb.add(h2 = createMenu("Help 2", false));  // h2 replaced h1
        mb.setHelpMenu(h2);

        mb.add(createMenu("four", false));

        return f;
    }

    private static Menu createMenu(String name, boolean tearOff) {
        Menu m = new Menu(name, tearOff);
        m.add(new MenuItem(name + " item 1"));
        m.add(new MenuItem(name + " item 2"));
        m.add(new MenuItem(name + " item 3"));
        return m;
    }
}

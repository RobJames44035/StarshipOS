/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import java.awt.Font;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

/*
 * @test
 * @bug 4066657 8009454
 * @requires os.family != "mac"
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Tests that setting a font on the Menu with MenuItem takes effect.
 * @run main/manual MenuSetFontTest
 */

public class MenuSetFontTest {

    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                    Look at the menu in the upper left corner of the 'SetFont Test' frame.
                    Click on the "File" menu. You will see "menu item" item.
                    Press Pass if menu item is displayed using bold and large font,
                    otherwise press Fail.
                    If you do not see menu at all, press Fail.""";

        PassFailJFrame.builder()
                .title("MenuSetFontTest")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(MenuSetFontTest::createAndShowUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createAndShowUI() {
        Frame frame = new Frame("SetFont Test");
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem item = new MenuItem("menu item");
        menu.add(item);
        menuBar.add(menu);
        menuBar.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        frame.setMenuBar(menuBar);
        frame.setSize(300, 200);
        return frame;
    }
}

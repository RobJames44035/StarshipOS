/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

/*
 * @test
 * @bug 4071086
 * @key headful
 * @summary Test dynamically adding and removing a menu bar
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual AddRemoveMenuBarTest_4
 */

public class AddRemoveMenuBarTest_4 {

    private static final String INSTRUCTIONS = """
            There is a frame with a menubar and a single button.

            The button is labelled 'Add new MenuBar'.

            If you click the button, the menubar is replaced with another menubar.
            This can be done repeatedly.

            The <n>-th menubar contains one menu, 'TestMenu<n>',
            with two items, 'one <n>' and 'two <n>'.

            Click again to replace the menu bar with another menu bar.

            After a menubar has been replaced with another menubar,
            the frame should not be resized nor repositioned on the screen.

            Upon test completion, click Pass or Fail appropriately.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("AddRemoveMenuBarTest_4 Instructions")
                .instructions(INSTRUCTIONS)
                .testTimeOut(5)
                .rows(18)
                .columns(45)
                .testUI(AddRemoveMenuBar_4::new)
                .build()
                .awaitAndCheck();
    }
}

class AddRemoveMenuBar_4 extends Frame {
    int count = 1;
    MenuBar mb = null;

    AddRemoveMenuBar_4() {
        super("AddRemoveMenuBar_4");
        setLayout(new FlowLayout());

        Button b = new Button("Add new MenuBar");
        b.addActionListener((e) -> createMenuBar());
        add(b);

        createMenuBar();

        setSize(300, 300);
    }

    void createMenuBar() {
        if (mb != null) {
            remove(mb);
        }

        mb = new MenuBar();
        Menu m = new Menu("TestMenu" + count);
        m.add(new MenuItem("one " + count));
        m.add(new MenuItem("two " + count));
        count++;
        mb.add(m);
        setMenuBar(mb);
    }
}

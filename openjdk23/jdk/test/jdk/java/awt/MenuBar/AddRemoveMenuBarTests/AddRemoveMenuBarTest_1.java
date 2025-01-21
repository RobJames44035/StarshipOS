/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * @test
 * @bug 4028130
 * @summary Test dynamically adding and removing a menu bar
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual AddRemoveMenuBarTest_1
 */

public class AddRemoveMenuBarTest_1 {

    private static final String INSTRUCTIONS = """
        An initially empty frame should appear.

        Click anywhere in the frame to add a menu bar at the top of the frame.

        Click again to replace the menu bar with another menu bar.

        Each menu bar has one (empty) menu, labelled with the
        number of the menu bar appearing.

        After a menubar is added, the frame should not be resized nor repositioned
        on the screen;

        it should have the same size and position.

        Upon test completion, click Pass or Fail appropriately.
        """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("AddRemoveMenuBarTest_1 Instructions")
                .instructions(INSTRUCTIONS)
                .testTimeOut(5)
                .rows(18)
                .columns(45)
                .testUI(AddRemoveMenuBar_1::new)
                .build()
                .awaitAndCheck();
    }
}

class AddRemoveMenuBar_1 extends Frame {
    int menuCount;

    AddRemoveMenuBar_1() {
        super("AddRemoveMenuBar_1");
        setSize(200, 200);
        menuCount = 0;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setMenuBar();
            }
        });
    }

    void setMenuBar() {
        MenuBar bar = new MenuBar();
        bar.add(new Menu(Integer.toString(menuCount++)));
        setMenuBar(bar);
    }
}

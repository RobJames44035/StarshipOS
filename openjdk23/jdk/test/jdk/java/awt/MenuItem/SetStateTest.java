/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 5106833
  @summary NullPointerException in XMenuPeer.repaintMenuItem
  @key headful
  @run main SetStateTest
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.CheckboxMenuItem;

public class SetStateTest {
    Frame frame;
    public static void main(String[] args) throws Exception {
        SetStateTest test = new SetStateTest();
        test.start();
    }

    public void start () throws Exception {
        try {
            EventQueue.invokeAndWait(() -> {
                frame = new Frame("SetStateTest");
                MenuBar bar = new MenuBar();
                Menu menu = new Menu("Menu");
                CheckboxMenuItem checkboxMenuItem = new CheckboxMenuItem("Item");
                bar.add(menu);
                frame.setMenuBar(bar);
                menu.add(checkboxMenuItem);
                checkboxMenuItem.setState(true);
                frame.setSize(300, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                System.out.println("Test PASSED!");
            });
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}

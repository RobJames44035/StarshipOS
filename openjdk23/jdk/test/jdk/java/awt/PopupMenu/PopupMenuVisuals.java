/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */
/*
 * @test
 * @bug 6180413 6184485 6267144
 * @summary test for popup menu visual bugs in XAWT
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PopupMenuVisuals
*/

import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class PopupMenuVisuals {
    private static final String INSTRUCTIONS = """
         This test should show a button 'Popup'.
         Click on the button. A popup menu should be shown.
         If following conditions are met:
          - Menu is disabled
          - Menu has caption 'Popup menu' (only applicable for linux)
          - Menu items don't show shortcuts

         Click Pass else click Fail.""";

    static PopupMenu pm;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("PopupMenu Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(PopupMenuVisuals::createTestUI)
                .build()
                .awaitAndCheck();
    }

    static class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pm.show(frame, 0, 0);
        }
    }

    private static Frame createTestUI() {
        Button b = new Button("Popup");
        pm = new PopupMenu("Popup menu");
        MenuItem mi1 = new MenuItem("Item 1");
        CheckboxMenuItem mi2 = new CheckboxMenuItem("Item 2");
        CheckboxMenuItem mi3 = new CheckboxMenuItem("Item 3");
        Menu sm = new Menu("Submenu");

        //Get things going.  Request focus, set size, et cetera
        frame = new Frame("PopupMenuVisuals");
        frame.setSize (200,200);
        frame.validate();

        frame.add(b);
        b.addActionListener(new Listener());
        mi1.setShortcut(new MenuShortcut(KeyEvent.VK_A));
        pm.add(mi1);
        pm.add(mi2);
        pm.add(mi3);
        pm.add(sm);
        sm.add(new MenuItem("Item"));
        pm.setEnabled(false);
        mi3.setState(true);
        frame.add(pm);
        return frame;
    }

}

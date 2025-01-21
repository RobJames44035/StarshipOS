/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
  @test
  @bug 4234266
  @summary MenuItem throws NullPointer exception when setting the label with created peer.
  @key headful
  @run main SetLabelWithPeerCreatedTest
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class SetLabelWithPeerCreatedTest {
     Frame frame;
     public static void main(String[] args) throws Exception {
         SetLabelWithPeerCreatedTest test = new SetLabelWithPeerCreatedTest();
         test.start();
     }

    public void start() throws Exception {
        try {
            EventQueue.invokeAndWait(() -> {
                frame = new Frame("SetLabel with Peer Created Test");
                Menu menu = new Menu("Menu");
                MenuItem mi = new MenuItem("Item");
                MenuBar mb = new MenuBar();
                menu.add(mi);
                mb.add(menu);
                frame.setMenuBar(mb);
                frame.setSize(300, 200);
                frame.setLocationRelativeTo(null);
                mi.setLabel("new label");
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

/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
  @test
  @bug 4154099
  @summary Tests that calling removeNotify() on a Frame and then reshowing
            the Frame does not crash or lockup
  @key headful
  @run main RemoveNotifyTest
*/

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class RemoveNotifyTest {
    static Frame f;

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    f = new Frame();
                    f.setBounds(10, 10, 100, 100);
                    MenuBar bar = new MenuBar();
                    Menu menu = new Menu();
                    menu.add(new MenuItem("foo"));
                    bar.add(menu);
                    f.setMenuBar(bar);

                    for (int j = 0; j < 5; j++) {
                        f.setVisible(true);
                        f.removeNotify();
                    }
                } finally {
                    if (f != null) {
                        f.dispose();
                    }
                }
            }
        });

      System.out.println("done");

    }

 }// class RemoveNotifyTest

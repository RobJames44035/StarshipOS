/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
  @test
  @bug 4186641 4242461
  @summary JMenu.getPopupMenuOrigin() protected (not privet) now.
  @key headful
  @run main bug4186641
*/

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;


public class bug4186641 {

    volatile static JFrame fr;

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            init();
            if (fr != null) {
                fr.dispose();
            }
        });
    }

    public static void init() {
        class TestJMenu extends JMenu {
            public TestJMenu() {
                super("Test");
            }

            void test() {
                Point testpoint = getPopupMenuOrigin();
            }
        }

        TestJMenu mnu = new TestJMenu();
        fr = new JFrame("bug4186641");
        JMenuBar mb = new JMenuBar();
        fr.setJMenuBar(mb);
        mb.add(mnu);
        JMenuItem mi = new JMenuItem("test");
        mnu.add(mi);
        fr.setSize(100,100);
        fr.setVisible(true);
        mnu.setVisible(true);

        mnu.test();
    }
}

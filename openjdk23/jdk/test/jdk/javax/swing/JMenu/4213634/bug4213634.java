/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/*
 * @test
 * @key headful
 * @bug 4213634 8017187
 * @author Scott Violet
 * @library ../../regtesthelpers
 * @build Util
 * @run main bug4213634
 */


public class bug4213634 {

    private JMenu menu;

    private JFrame frame;

    public static void main(String[] args) throws Throwable {
        new bug4213634();
    }

    bug4213634() throws AWTException, InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });

        test();
    }

    public  void createAndShowGUI() {
        frame = new JFrame("TEST");
        JMenuBar mb = new JMenuBar();
        menu = mb.add(createMenu("1 - First Menu", true));
        mb.add(createMenu("2 - Second Menu", false));
        frame.setJMenuBar(mb);
        JTextArea ta = new JTextArea("This test dedicated to Nancy and Kathleen, testers and bowlers extraordinaire\n\n\nNo exception means pass.");
        frame.getContentPane().add("Center", ta);
        JButton button = new JButton("Test");
        frame.getContentPane().add("South", button);
        frame.setBounds(100, 100, 400, 400);
        frame.setVisible(true);
        button.requestFocusInWindow();
    }

    private void test() throws AWTException, InterruptedException, InvocationTargetException {
        Robot robot = new Robot();
        robot.setAutoDelay(50);
        robot.waitForIdle();

        Util.hitMnemonics(robot, KeyEvent.VK_1);
        robot.waitForIdle();

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                if (!menu.isSelected()) {
                    throw new RuntimeException(
                        "Failed: Menu didn't remain posted at end of test");
                } else {
                    System.out.println("Test passed!");
                    frame.dispose();
                }
            }
        });
    }
    private JMenu createMenu(String str, boolean bFlag) {
        JMenuItem menuitem;
        JMenu menu = new JMenu(str);
        menu.setMnemonic(str.charAt(0));

        for(int i = 0; i < 10; i ++) {
            menuitem = new JMenuItem("JMenuItem" + i);
            menuitem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    throw new RuntimeException(
                        "Failed: Mnemonic activated");
                }
            });
            if(bFlag)
                menuitem.setMnemonic('0' + i);
            menu.add(menuitem);
        }
        return menu;
    }
}

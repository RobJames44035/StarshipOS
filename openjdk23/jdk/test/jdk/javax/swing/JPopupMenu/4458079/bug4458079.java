/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4458079
 * @library ../../regtesthelpers
 * @build Util
 * @summary Tests calling removeAll() from PopupMenuListener
 * @author Peter Zhelezniakov
 * @run main bug4458079
 */

import java.awt.Robot;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.KeyEvent;

public class bug4458079 extends JFrame implements PopupMenuListener {
    public JMenu menu;

    static volatile boolean itemASelected = false;
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);
        // move mouse outside menu to prevent auto selection
        robot.mouseMove(100,100);
        robot.waitForIdle();

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                new bug4458079().createAndShowGUI();
            }
        });

        robot.waitForIdle();
        robot.delay(1000);
        Util.hitMnemonics(robot, KeyEvent.VK_M);

        robot.waitForIdle();

        Util.hitKeys(robot, KeyEvent.VK_DOWN);
        Util.hitKeys(robot, KeyEvent.VK_ENTER);

        robot.waitForIdle();

        if (!itemASelected) {
            throw new RuntimeException("Test failed: arrow key traversal in JMenu broken!");
        }
    }
    public void createAndShowGUI() {
        JMenuBar bar = new JMenuBar();
        menu = new JMenu("Menu");
        menu.add(new JMenuItem("1"));
        menu.add(new JMenuItem("2"));
        menu.setMnemonic(KeyEvent.VK_M);
        menu.getPopupMenu().addPopupMenuListener(this);
        bar.add(menu);

        setJMenuBar(bar);
        getContentPane().add(new JButton(""));
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void rebuildMenu() {
        menu.removeAll();
        final String itemCommand = "A";
        JMenuItem item = new JMenuItem(itemCommand);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JMenuItem item = ((JMenuItem)e.getSource());
                if (e.getActionCommand() == itemCommand) {
                    itemASelected = true;
                }
            }
        });
        menu.add(item);
        menu.add(new JMenuItem("B"));
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        rebuildMenu();
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    public void popupMenuCanceled(PopupMenuEvent e) {}
}

/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.util.Date;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
  * This tests Swing Menus by posting key events to the EventQueue
  * Each time a menu is selected ActionEvent/MenuEvent is generated
  * and that event causes the menu text to be appended to a JList
  */

public class JMTest_03 extends AbstractSwingTest {
    JList           list;
    JMenuBar        jmenubar = null;

    int             nMenuCount = 2;
    int             nMenuItemCount = 4;
    int             ENTER = 10;
    int             LEFT = 37;
    int             RIGHT = 39;
    int             DOWN = 40;
    int             UP = 38;

    /**
     * This test cannot run as an applet because it
     * posts events to the event queue
     */
    public boolean canRunInApplet() {
        return false;
    }

    public JComponent getTestComponent() {
        JPanel panel = new JPanel();

        JMenu           jmenu;
        JMenuItem       jmenuitem;

        panel.setLayout(new BorderLayout());

        jmenubar = new JMenuBar();
        for (int i = 0; i < nMenuCount; i ++) {
            jmenu = new JMenu("JMenu" + i);
            jmenu.setMnemonic('0' + i);
            jmenubar.add(jmenu);

            for (int j = 0; j < nMenuItemCount; j ++) {
                jmenuitem = new JMenuItem("JMenuItem" + i + j);
                jmenuitem.setMnemonic('0' + j);
                jmenuitem.addActionListener(new MyActionListener());
                jmenu.add(jmenuitem);
            }
        }

        panel.add("North", jmenubar);

        list = new JList(new DefaultListModel());
        list.setFont(new Font("Serif", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(list);
        panel.add("Center", scrollPane);
        return panel;
    }

    public String getTestName() {
        return "JMTest_03";
    }

    public void runTest() {
        for (int i = 0; i < 10; i++) {
            testMenu();
        }
    }

    public void testMenu() {
        FireEvents();
    }

    @SuppressWarnings("deprecation")
    public void FireEvents() {
        int nCount = jmenubar.getMenuCount();
        JMenuItem menuitem;
        KeyEvent key;
        int firstMnem;
        JMenu   menu;

        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        for (int i = 0; i < nCount; i++) {
            menu = jmenubar.getMenu(i);
            int nItemCount = menu.getItemCount();

            for (int j = 0; j < nItemCount; j ++) {
                firstMnem = menu.getMnemonic();
                key = new KeyEvent(menu, KeyEvent.KEY_PRESSED,
                                   new Date().getTime(), KeyEvent.ALT_DOWN_MASK, firstMnem);
                queue.postEvent(key);
                rest();

                int mnem = menu.getItem(j).getMnemonic();

                key = new KeyEvent(menu, KeyEvent.KEY_PRESSED, new Date().getTime(), 0, mnem);
                queue.postEvent(key);
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        runStandAloneTest(new JMTest_03());
    }

    public class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AbstractButton comp = (AbstractButton) e.getSource();
            Display(comp.getText());
        }
    }

    public void Display(String str) {
        DefaultListModel  lm = (DefaultListModel) list.getModel();
        lm.addElement(str);
        int nSize = lm.getSize();
        list.setSelectedIndex(nSize - 1);
        list.requestFocus();
    }
}

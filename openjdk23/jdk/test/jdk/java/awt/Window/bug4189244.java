/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4189244
 * @summary Swing Popup menu is not being refreshed (cleared) under a Dialog
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @requires (os.family == "windows")
 * @run main/manual bug4189244
*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class bug4189244 {

    private static final String INSTRUCTIONS = """
         This is Windows only test!

         Click right button on frame to show popup menu.
         (menu should be placed inside frame otherwise bug is not reproducible)
         click on any menu item (dialog will be shown).
         close dialog.
         if you see part of popupmenu, under dialog, before it is closed,
         then test failed, else passed.""";

    public static void main(String[] args) throws Exception {
         PassFailJFrame.builder()
                .title("bug4189244 Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int)INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(bug4189244::createTestUI)
                .build()
                .awaitAndCheck();
    }


    private static JFrame createTestUI() {
        RefreshBug panel = new RefreshBug();
        JFrame frame = new JFrame("Popup refresh bug");

        frame.add(panel, BorderLayout.CENTER);
        panel.init();
        frame.setSize(400, 400);
        return frame;
    }
}

class RefreshBug extends JPanel implements ActionListener {
    JPopupMenu _jPopupMenu = new JPopupMenu();

    public void init() {
        JMenuItem menuItem;
        JButton jb = new JButton("Bring the popup here and select an item");

        this.add(jb, BorderLayout.CENTER);

        for(int i = 1; i < 10; i++) {
            menuItem = new JMenuItem("Item " + i);
            menuItem.addActionListener(this);
            _jPopupMenu.add(menuItem);
        }

        MouseListener ml = new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                        _jPopupMenu.show(e.getComponent(),
                                         e.getX(), e.getY());
                }
            }
        };
        this.addMouseListener(ml);

        jb.addMouseListener(ml);

    }

    // An action is requested by the user
    public void actionPerformed(java.awt.event.ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                                      "Check if there is some popup left under me\n"+
                                      "if not, retry and let the popup appear where i am",
                                      "WARNING",
                                      JOptionPane.WARNING_MESSAGE);

    }
}

/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
  @test
  @bug 5029504
  @summary Empty JComboBox drop-down list is unexpectedly high
  @key headful
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class bug5029504 {

    static volatile boolean passed = true;
    static volatile JFrame mainFrame;
    static volatile JComboBox comboBox;
    static volatile BasicComboPopup ourPopup = null;

    public static void main(String[] args) throws Exception {
        try {
             SwingUtilities.invokeAndWait(bug5029504::createUI);
             runTest();
             if (!passed) {
                 throw new RuntimeException(
                    "Popup of empty JComboBox is too high. Test failed.");
             }
        } finally {
            if (mainFrame != null) {
                SwingUtilities.invokeAndWait(mainFrame::dispose);
            }
        }
    }

    static void createUI() {
        mainFrame = new JFrame("Bug4924758");
        comboBox = new JComboBox();
        comboBox.setUI(new MyComboBoxUI());
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (ourPopup != null) {
                    int comboHeight = comboBox.getHeight();
                    int popupHeight = ourPopup.getHeight();
                    if (popupHeight > comboHeight*2) {
                        passed = false;
                    }
                }
            }

            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(comboBox, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.validate();
        mainFrame.setVisible(true);
    }

    static void runTest() throws Exception {
        Robot robot = new Robot();
        robot.delay(2000);
        Point p = comboBox.getLocationOnScreen();
        Dimension size = comboBox.getSize();
        p.x += size.width / 2;
        p.y += size.height / 2;
        robot.mouseMove(p.x, p.y);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);
    }

    static class MyComboBoxUI extends BasicComboBoxUI {
        public void setPopupVisible(JComboBox c, boolean v) {
            if (popup instanceof BasicComboPopup) {
                ourPopup = (BasicComboPopup) popup;
            }
            super.setPopupVisible(c, v);
        }
    }
}

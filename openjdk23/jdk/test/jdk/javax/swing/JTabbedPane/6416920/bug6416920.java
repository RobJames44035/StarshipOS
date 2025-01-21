/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6416920
 * @summary Ensures that selected tab is painted properly in the scroll tab layout
 *         under WindowsLookAndFeel in Windows' "Windows XP" theme.
 * @author Mikhail Lapshin
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @run main bug6416920
 */

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Insets;

import jdk.test.lib.Platform;

public class bug6416920 extends BasicTabbedPaneUI {
    public AccessibleTabbedPaneLayout layout = new AccessibleTabbedPaneLayout();

    public static void main(String[] args) {

        if (!Platform.isWindows()) {
            return;
        }

        bug6416920 test = new bug6416920();
        test.layout.padSelectedTab(SwingConstants.TOP, 0);
        if (test.rects[0].width < 0) {
            throw new RuntimeException("A selected tab isn't painted properly " +
                    "in the scroll tab layout under WindowsLookAndFeel " +
                    "in Windows' \"Windows XP\" theme.");
        }
    }

    public bug6416920() {
        super();

        // Set parameters for the padSelectedTab() method
        selectedTabPadInsets = new Insets(0, 0, 0, 0);

        tabPane = new JTabbedPane();
        tabPane.setSize(100, 0);
        tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        rects = new Rectangle[1];
        rects[0] = new Rectangle(150, 0, 0, 0);
    }

    public class AccessibleTabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
        public void padSelectedTab(int tabPlacement, int selectedIndex) {
            super.padSelectedTab(tabPlacement, selectedIndex);
        }
    }
}

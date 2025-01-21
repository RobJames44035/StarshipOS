/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import org.jtregext.GuiTestListener;
import com.sun.swingset3.demos.tabbedpane.TabbedPaneDemo;
import static com.sun.swingset3.demos.tabbedpane.TabbedPaneDemo.*;
import static org.jemmy2ext.JemmyExt.getLabeledContainerOperator;
import static org.testng.AssertJUnit.*;
import javax.swing.UIManager;
import org.testng.annotations.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.testng.annotations.Listeners;

/*
 * @test
 * @key headful
 * @summary Verifies SwingSet3 TabbedPaneDemo by iterating through tab placement
 *          positions, opening each tab and verifying the tab gets selected.
 *
 * @library /sanity/client/lib/jemmy/src
 * @library /sanity/client/lib/Extensions/src
 * @library /sanity/client/lib/SwingSet3/src
 * @modules java.desktop
 *          java.logging
 * @build org.jemmy2ext.JemmyExt
 * @build com.sun.swingset3.demos.tabbedpane.TabbedPaneDemo
 * @run testng/timeout=600 TabbedPaneDemoTest
 */
@Listeners(GuiTestListener.class)
public class TabbedPaneDemoTest {

    @Test(dataProvider = "availableLookAndFeels", dataProviderClass = TestHelpers.class)
    public void test(String lookAndFeel) throws Exception {
        UIManager.setLookAndFeel(lookAndFeel);
        new ClassReference(TabbedPaneDemo.class.getCanonicalName()).startApplication();

        JFrameOperator mainFrame = new JFrameOperator(DEMO_TITLE);

        for (String tp : new String[]{TOP, LEFT, BOTTOM, RIGHT}) {
            testTabs(mainFrame, tp);
        }
    }

    public void testTabs(JFrameOperator mainFrame, String tabPlacement) throws Exception {
        ContainerOperator<?> rbCont = getLabeledContainerOperator(mainFrame, TAB_PLACEMENT);
        new JRadioButtonOperator(rbCont, tabPlacement).doClick();

        final String[] tabTitles = new String[]{CAMILLE, MIRANDA, EWAN, BOUNCE};
        for (int i = 0; i < tabTitles.length; i++) {
            String pageTitle = tabTitles[i];
            JTabbedPaneOperator tabOperator = new JTabbedPaneOperator(mainFrame);
            tabOperator.setVerification(true);
            tabOperator.selectPage(pageTitle);
        }
    }

}

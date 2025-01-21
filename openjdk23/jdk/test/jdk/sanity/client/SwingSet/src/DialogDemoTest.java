
/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
import org.jtregext.GuiTestListener;
import com.sun.swingset3.demos.dialog.DialogDemo;
import static com.sun.swingset3.demos.dialog.DialogDemo.*;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JDialog;
import javax.swing.UIManager;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;
import static org.jemmy2ext.JemmyExt.isIconified;
import static org.jemmy2ext.JemmyExt.ByClassChooser;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.ComponentChooser;
import static org.netbeans.jemmy.WindowWaiter.countWindows;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.testng.annotations.Listeners;

/*
 * @test
 * @key headful
 * @summary Verifies SwingSet3 DialogDemo by checking that separate JDialog is
 *          shown, it contains predefined label and no new dialogs are opened
 *          when the "Show JDialog..." button is clicked.
 *
 * @library /sanity/client/lib/jemmy/src
 * @library /sanity/client/lib/Extensions/src
 * @library /sanity/client/lib/SwingSet3/src
 * @modules java.desktop
 *          java.logging
 * @build org.jemmy2ext.JemmyExt
 * @build com.sun.swingset3.demos.dialog.DialogDemo
 * @run testng/timeout=600 DialogDemoTest
 */
@Listeners(GuiTestListener.class)
public class DialogDemoTest {

    private final ComponentChooser jDialogClassChooser = new ByClassChooser(JDialog.class);

    @Test(dataProvider = "availableLookAndFeels", dataProviderClass = TestHelpers.class)
    public void test(String lookAndFeel) throws Exception {
        UIManager.setLookAndFeel(lookAndFeel);
        new ClassReference(DialogDemo.class.getCanonicalName()).startApplication();
        JFrameOperator mainFrame = new JFrameOperator(DIALOG_DEMO_TITLE);
        JDialogOperator dialog = new JDialogOperator(DIALOG_TITLE);
        JButtonOperator showJDialogButton = new JButtonOperator(mainFrame, SHOW_BUTTON_TITLE);
        initialCheckWithLabel(mainFrame, dialog);
        checkShowDialogButton(dialog, showJDialogButton);
        TestHelpers.checkChangeSize(dialog, new Dimension(dialog.getSize().width * 2,
                dialog.getSize().height * 2));
        TestHelpers.checkChangeLocation(dialog, new Point(dialog.getLocation().x + 100,
                dialog.getLocation().y + 100));
    }

    private void initialCheckWithLabel(JFrameOperator frame, JDialogOperator jdo) {
        JLabelOperator label = new JLabelOperator(jdo);
        assertFalse("JFrame is not iconified", isIconified(frame));
        assertEquals("Only one JDialog is present", 1,
                countWindows(jDialogClassChooser));
        assertEquals(LABEL_CONTENT, label.getText());
    }

    private void checkShowDialogButton(JDialogOperator jdo, JButtonOperator jbo)
            throws InterruptedException {
        //Check that the button does not change the number of JDialog
        jbo.push();
        Thread.sleep(500);
        assertEquals("Only one JDialog is present", 1,
                countWindows(jDialogClassChooser));
        assertTrue("Check JDialog is visible", jdo.isVisible());
        jdo.requestClose();
        jdo.waitClosed();
        //Check that the button makes the JDialog visible
        //and that 1 jDialog is present.
        jbo.push();
        jdo.waitComponentVisible(true);
        Thread.sleep(500);
        assertEquals("Only one JDialog is present", 1,
                countWindows(jDialogClassChooser));
    }
}

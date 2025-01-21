/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import org.jtregext.GuiTestListener;
import com.sun.swingset3.demos.combobox.ComboBoxDemo;
import static org.testng.AssertJUnit.*;
import javax.swing.UIManager;
import org.testng.annotations.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import static com.sun.swingset3.demos.combobox.ComboBoxDemo.*;
import org.testng.annotations.Listeners;

/*
 * @test
 * @key headful
 * @summary Verifies ComboBoxes on SwingSet2 ComboBoxDemo page by selecting
 *          each value of each ComboBox.
 *
 * @library /sanity/client/lib/jemmy/src
 * @library /sanity/client/lib/Extensions/src
 * @library /sanity/client/lib/SwingSet3/src
 * @modules java.desktop
 *          java.logging
 * @build org.jemmy2ext.JemmyExt
 * @build com.sun.swingset3.demos.combobox.ComboBoxDemo
 * @run testng/timeout=600 ComboBoxDemoTest
 */
@Listeners(GuiTestListener.class)
public class ComboBoxDemoTest {

    private static enum ComboBoxInfo {

        PRESETS("Presets:"),
        HAIR("Hair:"),
        EYES_N_NOSE("Eyes & Nose:"),
        MOUTH("Mouth:");

        private final String comboBoxName;

        private ComboBoxInfo(String comboBoxName) {
            this.comboBoxName = comboBoxName;
        }

    }

    @Test(dataProvider = "availableLookAndFeels", dataProviderClass = TestHelpers.class)
    public void test(String lookAndFeel) throws Exception {
        UIManager.setLookAndFeel(lookAndFeel);
        new ClassReference(ComboBoxDemo.class.getCanonicalName()).startApplication();

        JFrameOperator frame = new JFrameOperator(DEMO_TITLE);
        for (ComboBoxInfo comboBoxInfo : ComboBoxInfo.values()) {
            comboBoxChecker(frame, comboBoxInfo);
        }
    }

    private void comboBoxChecker(JFrameOperator jfo, ComboBoxInfo comboBoxInfo) {
        JComboBoxOperator jcbo = new JComboBoxOperator(jfo, comboBoxInfo.ordinal());
        for (int i = 0; i < jcbo.getItemCount(); i++) {
            jcbo.selectItem(i);
            jcbo.waitItemSelected(i);
        }
    }

}

/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import org.jtregext.GuiTestListener;
import com.sun.swingset3.demos.spinner.SpinnerDemo;
import static com.sun.swingset3.demos.spinner.SpinnerDemo.DEMO_TITLE;
import java.text.DecimalFormat;
import javax.swing.UIManager;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JSpinnerOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.testng.annotations.Listeners;

/*
 * @test
 * @key headful
 * @summary Verifies SwingSet3 SpinnerDemo by adjusting each spinner value via
 *          the spinner button and checking text field value.
 *
 * @library /sanity/client/lib/jemmy/src
 * @library /sanity/client/lib/Extensions/src
 * @library /sanity/client/lib/SwingSet3/src
 * @modules java.desktop
 *          java.logging
 * @build org.jemmy2ext.JemmyExt
 * @build com.sun.swingset3.demos.spinner.SpinnerDemo
 * @run testng/timeout=600 SpinnerDemoTest
 */
@Listeners(GuiTestListener.class)
public class SpinnerDemoTest {

    private static final int SPINNERS_COUNT = 9;
    private static final DecimalFormat decimalFormat = new DecimalFormat();

    @Test(dataProvider = "availableLookAndFeels", dataProviderClass = TestHelpers.class)
    public void test(String lookAndFeel) throws Exception {
        UIManager.setLookAndFeel(lookAndFeel);
        new ClassReference(SpinnerDemo.class.getCanonicalName()).startApplication();

        JFrameOperator frame = new JFrameOperator(DEMO_TITLE);

        // Check changing different spinners
        for (int i = 0; i < SPINNERS_COUNT; i++) {
            changeValues(frame, i);
        }
    }

    private void changeValues(JFrameOperator jfo, int spinnerIndex) throws Exception {
        JSpinnerOperator spinner = new JSpinnerOperator(jfo, spinnerIndex);
        JTextFieldOperator jtfo = new JTextFieldOperator(spinner);
        float originalFieldValue = decimalFormat.parse(jtfo.getText()).floatValue();
        float finalFieldValue;

        // increment by one the value via spinner
        spinner.getIncreaseOperator().push();
        finalFieldValue = decimalFormat.parse(jtfo.getText()).floatValue();

        // check that the value was increased
        assertTrue("Increment Spinner " + spinner
                + " (originalFieldValue, actual value: " + originalFieldValue + " "
                + "< finalFieldValue, actual value = " + finalFieldValue + ")",
                originalFieldValue < finalFieldValue);

        // decrease value via spinner
        spinner.getDecreaseOperator().push();
        finalFieldValue = decimalFormat.parse(jtfo.getText()).floatValue();

        // check that the value was decrimented
        assertTrue("Decrement Spinner " + spinner
                + " (originalFieldValue, actual value: " + originalFieldValue + " "
                + ">= finalFieldValue, actual value = " + finalFieldValue + ")",
                originalFieldValue >= finalFieldValue);
    }

}

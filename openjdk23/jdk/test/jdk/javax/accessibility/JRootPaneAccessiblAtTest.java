/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 4699544
 * @key headful
 * @summary AccessibleJRootPane always returns null for getAccessibleAt
 * @run main JRootPaneAccessiblAtTest
 */
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class JRootPaneAccessiblAtTest extends JFrame {

    public JRootPaneAccessiblAtTest() {
        JRootPane rootPane = getRootPane();
        AccessibleComponent accessibleComponent =
            rootPane.getAccessibleContext().getAccessibleComponent();
        Accessible accessible = accessibleComponent
            .getAccessibleAt(accessibleComponent.getLocation());
        if (accessible == null) {
            throw new RuntimeException("Test Failed: AccessibleJRootPane "
                + "always returns null for getAccessibleAt()");
        } else {
            System.out.println("Test Passed: AccessibilityJRootPane returns "
                + accessible + " for getAccessibleAt()");
        }
    }

    public static void main(String args[]) throws Exception {
        SwingUtilities.invokeAndWait(() -> new JRootPaneAccessiblAtTest());
    }
}


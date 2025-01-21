/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */


/*
  @test
  @bug 8069268
  @summary Tests that only one ContainerListener exists for AccessibleJComponent of JRootPane
  @author Vivi An
*/
import javax.swing.*;
import java.awt.event.*;
import javax.accessibility.*;

public class bug8069268{
    public static void main(String[] args) throws Exception {
        TestableRootPane rootPane = new TestableRootPane();

        // Get accesibleContext and then AccessibleJComponent, call the function
        // addPropertyChangeListener to trigger container listener to be added
        AccessibleContext acc = rootPane.getAccessibleContext();
        JComponent.AccessibleJComponent accJ = (JComponent.AccessibleJComponent) acc;
        accJ.addPropertyChangeListener(null);

        // Test how many container listener(s) exist(s), should only have 1
        if (!rootPane.testContainerListener())
            throw new RuntimeException("Failed test for bug 8069268");
    }

    private static class TestableRootPane extends JRootPane {
        public boolean testContainerListener() {
            boolean result = false;
            ContainerListener[] listeners = getContainerListeners();
            System.out.println("ContainerListener number is " + listeners.length);
            result = (listeners.length == 1) ? true : false;
            return result;
        }
    }
}

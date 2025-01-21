/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 6225100
  @summary FocusTraversalPolicy.getInitialComponent does not work as expected
  @run main FocusTraversalPolicyIAE
*/

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

public class FocusTraversalPolicyIAE {
    public static void main(String[] args) {
        CustomFocusTraversalPolicy cftp = new CustomFocusTraversalPolicy();
        try {
            cftp.getInitialComponent(null);
            throw new RuntimeException("Test failed. No exceptions thrown.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Test passed.");
        } catch (NullPointerException npe) {
            throw new RuntimeException("Test failed. Unexpected NPE thrown: " + npe);
        } catch (Exception e) {
            throw new RuntimeException("Test failed. Unexpected exception thrown: " + e);
        }
    }
}

class CustomFocusTraversalPolicy extends FocusTraversalPolicy {
    public Component getComponentAfter(Container focusCycleRoot,
                                       Component aComponent) {
        return null;
    }

    public Component getComponentBefore(Container focusCycleRoot,
                                        Component aComponent) {
        return null;
    }

    public Component getDefaultComponent(Container focusCycleRoot) {
        return null;
    }

    public Component getFirstComponent(Container focusCycleRoot) {
        return null;
    }

    public Component getLastComponent(Container focusCycleRoot) {
        return null;
    }
}

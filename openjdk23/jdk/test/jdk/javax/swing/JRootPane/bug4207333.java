/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.JRootPane;
import java.lang.reflect.Field;

/*
 * @test
 * @bug 4207333
 * @summary Inadvertant API regression in JRootPane
 * @run main bug4207333
 */

public class bug4207333 {
    public static void main(String[] argv) {
        TestableRootPane rp = new TestableRootPane();
        rp.setDefaultButton(new JButton("Default, eh?"));

        if (!rp.test("defaultPressAction")) {
            throw new RuntimeException("Failed test for bug 4207333");
        }
        if (!rp.test("defaultReleaseAction")) {
            throw new RuntimeException("Failed test for bug 4207333");
        }
        System.out.println("Test Passed!");
    }

    private static class TestableRootPane extends JRootPane {
        public boolean test(String fieldName) {
            boolean result = false;
            try {
                Class superClass = getClass().getSuperclass();
                Field field = superClass.getDeclaredField(fieldName);
                Class fieldClass = field.getType();
                Class actionClass = Class.forName("javax.swing.Action");

                // Is the Field an Action?
                result = actionClass.isAssignableFrom(fieldClass);
            } catch (NoSuchFieldException pe) {
                // Not a bug if the fields are removed since their
                // type was a package private class!
                result = true;
            } catch (Exception iae) {
                System.out.println("Exception " + iae);
            }
            return result;
        }
    }
}

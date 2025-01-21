/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.text.ParseException;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatter;
/*
 * @test
 * @bug 8080972
 * @run main TestDefaultFormatter
 * @summary Audit Core Reflection in module java.desktop for places that will
 *          require changes to work with modules
 */

public class TestDefaultFormatter {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(TestDefaultFormatter::testDefaultFormatter);
    }
    private static void testDefaultFormatter() {
        testDefaultFormatter(new DefaultFormatter() {
        });
        testDefaultFormatter(new DefaultFormatter());
    }

    private static void testDefaultFormatter(DefaultFormatter formatter ) {
        try {
            System.out.println("formatter: " + formatter.getClass());
            formatter.setValueClass(UserValueClass.class);
            UserValueClass userValue = (UserValueClass) formatter.stringToValue("test");

            if (!userValue.str.equals("test")) {
                throw new RuntimeException("String value is wrong!");
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static class UserValueClass {

        String str;

        public UserValueClass(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return "UserValueClass: " + str;
        }
    }
}

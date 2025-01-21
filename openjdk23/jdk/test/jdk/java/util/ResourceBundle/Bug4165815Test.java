/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
    The fix for 4165815 has been backed out because of compatibility issues.
    Disabled this test temporarily until a better fix is found by removing
    the at-signs.
    test
    summary test Bug 4165815
    run main Bug4165815Test
    bug 4165815
*/
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 *  This is a regression test for the following bug:
 *  "If the path specified by the baseName argument to
 *  ResourceBundle.getBundle() begins with a leading slash, then the bundle
 *  is not found relative to the classpath.
 *
 *  Clearly, the leading slash was inappropriate, however this did work
 *  previously (pre 1.2) and should continue to work in the same fashion."
 *
 *  A Bundle base name should never contain a "/" and thus an
 *  IllegalArgumentException should be thrown.
 */
public class Bug4165815Test extends RBTestFmwk {
    public static void main(String[] args) throws Exception {
        new Bug4165815Test().run(args);
    }

    private static final String bundleName = "/Bug4165815Bundle";
    public void testIt() throws Exception {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, Locale.US);
            errln("ResourceBundle returned a bundle when it should not have.");
        } catch (IllegalArgumentException e) {
            //This is what we should get when the base name contains a "/" character.
        } catch (MissingResourceException e) {
            errln("ResourceBundle threw a MissingResourceException when it should have thrown an IllegalArgumentException.");
        }
    }
}

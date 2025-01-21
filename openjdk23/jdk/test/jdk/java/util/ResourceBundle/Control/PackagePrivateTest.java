/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * @test
 * @bug 4175293 5102289
 * @summary Test if package private ResourceBundles can be loaded.
 * @build PackagePrivateRB
 * @run main/timeout=300/othervm -esa PackagePrivateTest
 */

import java.io.*;
import java.util.*;
import static java.util.ResourceBundle.Control.*;

public class PackagePrivateTest {
    public static void main(String[] args) {
        ResourceBundle rb;

        // Make sure that the default Control can't load the package
        // private resource bundles.
        try {
            rb = ResourceBundle.getBundle("PackagePrivateRB");
            throw new RuntimeException(
                       "doesn't throw MissingResourceException with the default Control");
        } catch (MissingResourceException e) {
        }

        // Remove the dummy cache entry
        ResourceBundle.clearCache();

        rb = ResourceBundle.getBundle("PackagePrivateRB",
                new ResourceBundle.Control() {
                    @Override
                    public List<String> getFormats(String baseName) {
                        return FORMAT_CLASS;
                    }

                    @Override
                    public ResourceBundle newBundle(String baseName,
                                                    Locale locale,
                                                    String format,
                                                    ClassLoader loader,
                                                    boolean reload)
                        throws IllegalAccessException,
                               InstantiationException, IOException {
                        String bn = toBundleName(baseName, locale);
                        if ("java.class".equals(format)) {
                            try {
                                Class<? extends ResourceBundle> cl =
                                    (Class<? extends ResourceBundle>) loader.loadClass(bn);
                                return  cl.newInstance();
                            } catch (ClassNotFoundException e) {
                                //System.out.println("ClassNotFoundException: " + e.getMessage());
                            }
                            return null;
                        }
                        throw new IllegalArgumentException("unknown format: " + format);
                    }
                });
        String s = rb.getString("type");
        if (!s.equals("class (package1.PackagePrivateRB)")) {
            throw new RuntimeException("wrong type: got " + s + ", expected '" +
                                       "class (package1.PackagePrivateRB)'");
        }
    }
}

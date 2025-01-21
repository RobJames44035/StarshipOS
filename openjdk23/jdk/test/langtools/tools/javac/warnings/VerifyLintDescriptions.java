/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8033961
 * @summary Verify that all LintCategories have their descriptions filled.
 * @modules jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.resources:open
 *          jdk.compiler/com.sun.tools.javac.util
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.sun.tools.javac.code.Lint.LintCategory;
import com.sun.tools.javac.util.Log.PrefixKind;

public class VerifyLintDescriptions {
    public static void main(String... args) {
        ModuleLayer boot = ModuleLayer.boot();
        Module jdk_compiler = boot.findModule("jdk.compiler").get();
        ResourceBundle b = ResourceBundle.getBundle("com.sun.tools.javac.resources.javac",
                                                    Locale.US,
                                                    jdk_compiler);

        List<String> missing = new ArrayList<>();

        for (LintCategory lc : LintCategory.values()) {
            try {
                b.getString(PrefixKind.JAVAC.key("opt.Xlint.desc." + lc.option));
            } catch (MissingResourceException ex) {
                missing.add(lc.option);
            }
        }

        if (!missing.isEmpty()) {
            throw new UnsupportedOperationException("Lints that are missing description: " + missing);
        }
    }

}

/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4904495
 * @summary Compilation may go awry if we ask a symbol for its flags during
 *          javac's Enter phase, before the flags are generally available.
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */


import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.Reporter;
import jdk.javadoc.doclet.DocletEnvironment;

public class FlagsTooEarly implements Doclet {

    public static void main(String[] args) {
        String thisFile = "" +
            new java.io.File(System.getProperty("test.src", "."),
                             "FlagsTooEarly.java");

        String[] argarray = {
            "-docletpath", System.getProperty("test.classes", "."),
            "-doclet", "FlagsTooEarly",
            "-Xwerror",
            thisFile
        };
        if (jdk.javadoc.internal.tool.Main.execute(argarray) != 0)
            throw new Error("Javadoc encountered warnings or errors.");
    }

    /*
     * The world's simplest doclet.
     */
    public boolean run(DocletEnvironment root) {
        return true;
    }

    /*
     * The following sets up the scenario for triggering the (potential) bug.
     */
    C2 c;
    static class C1 { }
    static class C2 { }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public Set<Option> getSupportedOptions() {
        return Collections.emptySet();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    public void init(Locale locale, Reporter reporter) {
        return;
    }
}

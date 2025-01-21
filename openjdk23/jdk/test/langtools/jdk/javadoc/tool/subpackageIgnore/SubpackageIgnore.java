/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4773013
 * @summary When hunting subpackages, silently ignore any directory name that
 *          can't be part of a subpackage.
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class SubpackageIgnore implements Doclet {

    public static void main(String[] args) {
        String[] cmds = new String[] {
            "-docletpath",
            System.getProperty("test.classes"),
            "-doclet",
            "SubpackageIgnore",
            "-Xwerror",
            "-sourcepath",
            System.getProperty("test.src", "."),
            "-subpackages",
            "pkg1"};
        if (jdk.javadoc.internal.tool.Main.execute(cmds) != 0)
            throw new Error("Javadoc encountered warnings or errors.");
    }

    /*
     * The world's simplest doclet.
     */
    public boolean run(DocletEnvironment root) {
        return true;
    }

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

    @Override
    public void init(Locale locale, Reporter reporter) {
        // do nothing
    }
}

/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.CSS;
import javax.swing.text.html.StyleSheet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * @test
 * @bug 8234913
 * @library /test/lib
 * @summary The test project is launched with the option -verbose:class. If the exception is thrown, its class will be loaded.
 *          Since the exception is thrown, but then caught, it is not possible to catch it in the test case with try-catch.
 *          The class loader logs have to be monitored.
 * @run driver bug8234913
 */
public class bug8234913 {
    // Output analysis taken from test/hotspot/jtreg/runtime/logging/ClassLoadUnloadTest.java
    private static ProcessBuilder pb;

    static ProcessBuilder exec(String... args) throws Exception {
        List<String> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);
        Collections.addAll(argsList, "-Xmn8m");
        Collections.addAll(argsList, "-Dtest.class.path=" + System.getProperty("test.class.path", "."));
        Collections.addAll(argsList, FontSizePercentTest.class.getName());
        return ProcessTools.createLimitedTestJavaProcessBuilder(argsList.toArray(new String[argsList.size()]));
    }

    static void checkFor(String... outputStrings) throws Exception {
        OutputAnalyzer out = new OutputAnalyzer(pb.start());
        for (String s : outputStrings) {
            out.shouldContain(s);
        }
        out.shouldHaveExitValue(0);
    }

    static void checkAbsent(String... outputStrings) throws Exception {
        OutputAnalyzer out = new OutputAnalyzer(pb.start());
        for (String s : outputStrings) {
            out.shouldNotContain(s);
        }
        out.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        pb = exec("-verbose:class");
        checkFor("[class,load] javax.swing.text.html.CSS$LengthUnit"); // the class that parses %
        checkAbsent("[class,load] java.lang.NumberFormatException");
    }

    static class FontSizePercentTest {
        public static void main(String... args) throws Exception {
            StyleSheet ss = new StyleSheet();
            MutableAttributeSet attr = new SimpleAttributeSet();
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "100%");

            attr.removeAttribute(CSS.Attribute.FONT_SIZE);
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "10pt");

            attr.removeAttribute(CSS.Attribute.FONT_SIZE);
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "10px");

            attr.removeAttribute(CSS.Attribute.FONT_SIZE);
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "10mm");

            attr.removeAttribute(CSS.Attribute.FONT_SIZE);
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "10cm");

            attr.removeAttribute(CSS.Attribute.FONT_SIZE);
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "10pc");

            attr.removeAttribute(CSS.Attribute.FONT_SIZE);
            ss.addCSSAttribute(attr, CSS.Attribute.FONT_SIZE, "10in");
        }
    }
}

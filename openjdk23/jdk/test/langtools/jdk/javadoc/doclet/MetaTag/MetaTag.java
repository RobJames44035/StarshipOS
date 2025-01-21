/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug      4034096 4764726 6235799 8182765 8196202
 * @summary  Add support for HTML keywords via META tag for
 *           class and member names to improve API search
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main MetaTag
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import javadoc.tester.JavadocTester;

public class MetaTag extends JavadocTester {

    /**
     * The entry point of the test.
     * @param args the array of command line arguments
     * @throws Exception if the test fails
     */
    public static void main(String... args) throws Exception {
        var tester = new MetaTag();
        tester.runTests();
    }

    @Test
    public void testStandard() {
        javadoc("-d", "out-1",
                "-sourcepath", testSrc,
                "-keywords",
                "-doctitle", "Sample Packages",
                "p1", "p2");

        checkExit(Exit.OK);

        checkMeta("dc.created", true);
    }

    @Test
    public void testNoTimestamp() {
        javadoc("-d", "out-2",
                "-sourcepath", testSrc,
                "-notimestamp",
                "-doctitle", "Sample Packages",
                "p1", "p2");
        checkExit(Exit.OK);

        // No keywords when -keywords is not used.
        checkMeta("dc.created", false);
    }

    void checkMeta(String metaNameDate, boolean found) {
        checkOutput("p1/C1.html", found,
                """
                    <meta name="keywords" content="p1.C1 class">""",
                """
                    <meta name="keywords" content="field1">""",
                """
                    <meta name="keywords" content="field2">""",
                """
                    <meta name="keywords" content="method1()">""",
                """
                    <meta name="keywords" content="method2()">""");

        checkOutput("p1/package-summary.html", found,
                """
                    <meta name="keywords" content="p1 package">""");

        checkOutput("index.html", found,
                """
                    <meta name="keywords" content="Overview, Sample Packages">""");

        // NOTE: Hopefully, this regression test is not run at midnight.  If the output
        // was generated yesterday and this test is run today, this check could fail ...
        // so make sure the date has not changed since the test started
        String date = date();
        if (date.equals(startDate)) {
            checkOutput("index.html", found,
                    "<meta name=\"" + metaNameDate + "\" content=\"" + date + "\">");
        }
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String startDate = date();

    static String date() {
        return dateFormat.format(new Date());
    }
}

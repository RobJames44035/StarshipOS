/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8004832
 * @summary Add new doclint package
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 */

import jdk.javadoc.internal.doclint.DocLint;

public class OptionTest {
    public static void main(String... args) throws Exception {
        new OptionTest().run();
    }

    String[] positiveTests = {
        "-Xmsgs",
        "-Xmsgs:all",
        "-Xmsgs:none",
        "-Xmsgs:accessibility",
        "-Xmsgs:html",
        "-Xmsgs:missing",
        "-Xmsgs:reference",
        "-Xmsgs:syntax",
        "-Xmsgs:html/public",
        "-Xmsgs:html/protected",
        "-Xmsgs:html/package",
        "-Xmsgs:html/private",
        "-Xmsgs:-html/public",
        "-Xmsgs:-html/protected",
        "-Xmsgs:-html/package",
        "-Xmsgs:-html/private",
        "-Xmsgs:html,syntax",
        "-Xmsgs:html,-syntax",
        "-Xmsgs:-html,syntax",
        "-Xmsgs:-html,-syntax",
        "-Xmsgs:html/public,syntax",
        "-Xmsgs:html,syntax/public",
        "-Xmsgs:-html/public,syntax/public"
    };

    String[] negativeTests = {
        "-typo",
        "-Xmsgs:-all",
        "-Xmsgs:-none",
        "-Xmsgs:typo",
        "-Xmsgs:html/typo",
        "-Xmsgs:html/public,typo",
        "-Xmsgs:html/public,syntax/typo",
    };

    void run() throws Exception {
        test(positiveTests, true);
        test(negativeTests, false);

        if (errors > 0)
            throw new Exception(errors + " errors occurred");
    }

    void test(String[] tests, boolean expect) {
        DocLint docLint = new DocLint();
        for (String test: tests) {
            System.err.println("test: " + test);
            boolean found = docLint.isValidOption(test);
            if (found != expect)
                error("Unexpected result: " + found + ",expected: " + expect);
        }
    }

    void error(String msg) {
        System.err.println("Error: " + msg);
        errors++;
    }

    int errors;
}

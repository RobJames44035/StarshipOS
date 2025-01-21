/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8195796
 * @summary Add normalize and relative methods to DocPath
 * @library /tools/lib
 * @modules jdk.javadoc/jdk.javadoc.internal.doclets.toolkit.util
 * @build toolbox.TestRunner
 * @run main TestDocPaths
 */

import jdk.javadoc.internal.doclets.toolkit.util.DocPath;
import toolbox.TestRunner;

public class TestDocPaths extends TestRunner {

    public static void main(String... args) throws Exception {
        var tester = new TestDocPaths();
        tester.runTests();
    }

    TestDocPaths() {
        super(System.err);
    }

    @Test
    public void testNormalize() {
        testNormalize("", "");
        testNormalize(".", "");
        testNormalize("a/b", "a/b");
        testNormalize("a//b", "a/b");
        testNormalize("./b", "b");
        testNormalize("a/.", "a");
        testNormalize("a/./b", "a/b");
        testNormalize("../b", "../b");
        testNormalize("a/..", "");
        testNormalize("a/../b", "b");
        testNormalize("a/../../b", "../b");
        testNormalize("a/./../b", "b");
        testNormalize("./../b", "../b");
        testNormalize("a/./../b", "b");
    }

    private void testNormalize(String p, String expect) {
        out.println("test " + p);
        String found = DocPath.create(p).normalize().getPath();
        out.println("  result: " + found);
        if (!expect.equals(found)) {
            error("Mismatch:\n"
                + "  expect: " + expect);
        }
        out.println();
    }

    @Test
    public void testRelativize() {
        testRelativize("a/b/c/file.html", "file.html", "");
        testRelativize("a/b/c/file.html", "file2.html", "file2.html");
        testRelativize("a/b/c/file.html", "../../../a/b/file.html", "../file.html");
        testRelativize("a/b/c/file.html", "../../../a/b/c/file.html", "");
        testRelativize("a/b/c/file.html", "../../../a/b/c2/file.html", "../c2/file.html");
        testRelativize("a/b/c/file.html", "../../../a/b/c/d/file.html", "d/file.html");
    }

    private void testRelativize(String file, String href, String expect) {
        out.println("test " + file + " " + href);
        String found = DocPath.create(file)
                .relativize(DocPath.create(href))
                .getPath();
        out.println("  result: " + found);
        if (!expect.equals(found)) {
            error("Mismatch:\n"
                + "  expect: " + expect);
        }
        out.println();
    }
}

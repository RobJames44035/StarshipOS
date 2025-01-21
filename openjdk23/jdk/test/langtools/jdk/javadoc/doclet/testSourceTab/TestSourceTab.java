/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4510979
 * @summary Test to make sure that the source documentation is indented properly
 * when -linksourcetab is used.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestSourceTab
 */

import java.io.*;

import javadoc.tester.JavadocTester;

public class TestSourceTab extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestSourceTab();
        tester.runTests();
    }

    @Test
    public void test() throws Exception {
        String tmpSrcDir = "tmpSrc";
        String outdir1 = "out-tabLengthEight";
        String outdir2 = "out-tabLengthFour";
        initTabs(new File(testSrc), new File(tmpSrcDir));

        // Run Javadoc on a source file with that is indented with a single tab per line
        javadoc("-d", outdir1,
                "-sourcepath", tmpSrcDir,
                "-notimestamp",
                "-linksource",
                tmpSrcDir + "/SingleTab/C.java");
        checkExit(Exit.OK);

        // Run Javadoc on a source file with that is indented with a two tab per line
        // If we double the tabs and decrease the tab length by a half, the output should
        // be the same as the one generated above.
        javadoc("-d", outdir2,
                "-sourcepath", tmpSrcDir,
                "-notimestamp",
                "-sourcetab", "4",
                tmpSrcDir + "/DoubleTab/C.java");
        checkExit(Exit.OK);

        diff(outdir1, outdir2,
                "src-html/C.html",
                "C.html");
    }

    void initTabs(File from, File to) throws IOException {
        for (File f: from.listFiles()) {
            File t = new File(to, f.getName());
            if (f.isDirectory()) {
                initTabs(f, t);
            } else if (f.getName().endsWith(".java")) {
                write(t, read(f).replace("\\t", "\t"));
            }
        }
    }

    String read(File f) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append(NL);
            }
        }
        return sb.toString();
    }

    void write(File f, String s) throws IOException {
        f.getParentFile().mkdirs();
        try (Writer out = new FileWriter(f)) {
            out.write(s);
        }
    }
}

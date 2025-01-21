/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8149757
 * @summary Test that javac uses the correct version of a class from a
 *          multi-release jar on classpath
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JarTask toolbox.JavacTask
 * @run testng MultiReleaseJarTest
 */

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import toolbox.JarTask;
import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;


public class MultiReleaseJarTest {

    private final String main1 =
            "class Main {\n" +
            "    Info info = new Info();\n" +
            "        \n" +
            "    void run() {\n" +
            "       System.out.println(info.get());\n" +
            "    }\n" +
            "}\n";

    private final String main2 =
            "class Main {\n" +
            "    Info info = new Info();\n" +
            "        \n" +
            "    void run() {\n" +
            "       System.out.println(info.getInfo());\n" +
            "    }\n" +
            "}\n";

    private final String info1 =
            "class Info {\n" +
            "    String get() {\n" +
            "       return \"some info\";\n" +
            "    }\n" +
            "}\n";

    private final String info2 =
            "class Info {\n" +
            "    String getInfo() {\n" +
            "       return \"some info\";\n" +
            "    }\n" +
            "}\n";

    private final String manifest =
        "Manifest-Version: 1.0\n" +
        "Multi-Release: true\n";

    private final ToolBox tb = new ToolBox();

    @BeforeClass
    public void setup() throws Exception {
        tb.createDirectories("classes", "classes/META-INF/versions/9");
        new JavacTask(tb)
                .outdir("classes")
                .sources(info1)
                .run();
        new JavacTask(tb)
                .outdir("classes/META-INF/versions/9")
                .sources(info2)
                .run();
        // This is a bogus multi-release jar file since the two Info classes
        // do not have the same public interface
        new JarTask(tb, "multi-release.jar")
                .manifest(manifest)
                .baseDir("classes")
                .files("Info.class", "META-INF/versions/9/Info.class")
                .run();
        tb.deleteFiles(
                "classes/META-INF/versions/9/Info.class",
                "classes/META-INF/versions/9",
                "classes/META-INF/versions",
                "classes/META-INF",
                "classes/Info.class"
        );
    }

    @AfterClass
    public void teardown() throws Exception {
        tb.deleteFiles(
                "multi-release.jar",
                "classes/Main.class",
                "classes"
        );
    }

    @Test(dataProvider="modes")
    // javac -d classes -cp multi-release.jar Main.java -> fails
    public void main1Runtime(Task.Mode mode) throws Exception {
        tb.writeFile("Main.java", main1);
        Task.Result result = new JavacTask(tb, mode)
                .outdir("classes")
                .classpath("multi-release.jar")
                .files("Main.java")
                .run(Task.Expect.FAIL, 1);
        result.writeAll();
        tb.deleteFiles("Main.java");

    }

    @Test(dataProvider="modes")
    // javac -d classes --release 8 -cp multi-release.jar Main.java -> succeeds
    public void main1Release8(Task.Mode mode) throws Exception {
        tb.writeFile("Main.java", main1);
        Task.Result result = new JavacTask(tb, mode)
                .outdir("classes")
                .options("--release", "8")
                .classpath("multi-release.jar")
                .files("Main.java")
                .run();
        result.writeAll();
        tb.deleteFiles("Main.java");
    }

    @Test(dataProvider="modes")
    // javac -d classes --release 9 -cp multi-release.jar Main.java -> fails
    public void main1Release9(Task.Mode mode) throws Exception {
        tb.writeFile("Main.java", main1);
        Task.Result result = new JavacTask(tb, mode)
                .outdir("classes")
                .options("--release", "9")
                .classpath("multi-release.jar")
                .files("Main.java")
                .run(Task.Expect.FAIL, 1);
        result.writeAll();
        tb.deleteFiles("Main.java");
    }

    @Test(dataProvider="modes")
    // javac -d classes -cp multi-release.jar Main.java -> succeeds
    public void main2Runtime(Task.Mode mode) throws Exception {
        tb.writeFile("Main.java", main2);
        Task.Result result = new JavacTask(tb, mode)
                .outdir("classes")
                .classpath("multi-release.jar")
                .files("Main.java")
                .run();
        result.writeAll();
        tb.deleteFiles("Main.java");

    }

    @Test(dataProvider="modes")
    // javac -d classes --release 8 -cp multi-release.jar Main.java -> fails
    public void main2Release8(Task.Mode mode) throws Exception {
        tb.writeFile("Main.java", main2);
        Task.Result result = new JavacTask(tb, mode)
                .outdir("classes")
                .options("--release", "8")
                .classpath("multi-release.jar")
                .files("Main.java")
                .run(Task.Expect.FAIL, 1);
        result.writeAll();
        tb.deleteFiles("Main.java");
    }

    @Test(dataProvider="modes")
    // javac -d classes --release 9 -cp multi-release.jar Main.java -> succeeds
    public void main2Release9(Task.Mode mode) throws Exception {
        tb.writeFile("Main.java", main2);
        Task.Result result = new JavacTask(tb, mode)
                .outdir("classes")
                .options("--release", "9")
                .classpath("multi-release.jar")
                .files("Main.java")
                .run();
        result.writeAll();
        tb.deleteFiles("Main.java");
    }

    @DataProvider(name="modes")
    public Object[][] createModes() {
        return new Object[][] {
            new Object[] {Task.Mode.API},
            new Object[] {Task.Mode.CMDLINE},
            new Object[] {Task.Mode.EXEC},
        };
    }
}


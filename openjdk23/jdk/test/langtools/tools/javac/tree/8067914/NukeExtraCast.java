/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8067914
 * @summary Redundant type cast nodes in AST (follow up from JDK-8043741)
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox
 * @run compile -XD-printsource T8067914.java
 * @run main NukeExtraCast
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import toolbox.ToolBox;

public class NukeExtraCast {

    public static void main(String[] args) throws Exception {
        ToolBox tb = new ToolBox();
        Path path1 = Paths.get(ToolBox.testClasses, "E.java");
        List<String> file1 = tb.readAllLines(path1);

        Path path2 = Paths.get(ToolBox.testSrc, "E.out");
        List<String> file2 = tb.readAllLines(path2);
        tb.checkEqual(file1, file2);
    }

}

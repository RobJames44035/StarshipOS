/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4587562
 * @summary tool: Indentation messed up for javadoc comments omitting preceding *
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @run main NoStar
 */

import java.util.*;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.Reporter;
import jdk.javadoc.doclet.DocletEnvironment;

/** First sentence.
0
 1
  2
   3
    4
     5
*/
public class NoStar implements Doclet
{
    public static void main(String[] args) {
        String[] argarray = {
            "-docletpath", System.getProperty("test.classes", "."),
            "-doclet", "NoStar",
            System.getProperty("test.src", ".") + java.io.File.separatorChar + "NoStar.java"
        };
        if (jdk.javadoc.internal.tool.Main.execute(argarray) != 0)
            throw new Error();
    }

    public boolean run(DocletEnvironment root) {
        Set<TypeElement> classes = ElementFilter.typesIn(root.getIncludedElements());
        if (classes.size() != 1)
            throw new Error("1 " + Arrays.asList(classes));
        TypeElement self = classes.iterator().next();
        DocTrees trees = root.getDocTrees();
        DocCommentTree docCommentTree = trees.getDocCommentTree(self);
        String c = docCommentTree.getFullBody().toString();
        System.out.println("\"" + c + "\"");
        return c.equals("""
            First sentence.
            0
             1
              2
               3
                4
                 5""");
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
        return;
    }
}

/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4959985
 * @summary Verify that (verbose) warnings are no longer generated when
 *          the default first-sentence algorithm doesn't match the
 *          BreakIterator algorithm.
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.Reporter;
import jdk.javadoc.doclet.DocletEnvironment;

public class BreakIteratorWarning implements Doclet {

    public static void main(String[] args) {
        String thisFile = "" +
            new java.io.File(System.getProperty("test.src", "."),
                             "BreakIteratorWarning.java");

        String[] argarray = {
            "-docletpath", System.getProperty("test.classes", "."),
            "-doclet", "BreakIteratorWarning",
            "-Xwerror",
            thisFile
        };
        if (jdk.javadoc.internal.tool.Main.execute(argarray) != 0)
            throw new Error("Javadoc encountered warnings or errors.");
    }

    List<VariableElement> getFields(TypeElement klass) {
        List<VariableElement> fields = new ArrayList<>();
        klass.getEnclosedElements()
                .stream()
                .filter((e) -> (e.getKind() == ElementKind.FIELD))
                .forEach((e) -> { fields.add((VariableElement)e);});
        return fields;
    }

    public boolean run(DocletEnvironment root) {
        TypeElement cd = ElementFilter.typesIn(root.getIncludedElements()).iterator().next();
        VariableElement fd = getFields(cd).get(0);
        DocTrees docTrees = root.getDocTrees();
        DocCommentTree docCommentTree = docTrees.getDocCommentTree(fd);
        List<? extends DocTree> firstSentence = docCommentTree.getFirstSentence();
        return true;
    }


    /**
     * "He'll never catch up!" the Sicilian cried.  "Inconceivable!"
     * "You keep using that word!" the Spaniard snapped.  "I do not
     * think it means what you think it means."
     *
     * <p> This comment used to trigger a warning, but no longer does.
     */
    public String author = "William Goldman";

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

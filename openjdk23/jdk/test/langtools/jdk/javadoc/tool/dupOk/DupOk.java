/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4673477
 * @summary The first definition found for each class should be documented
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.io.File;
import java.util.*;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.Reporter;
import jdk.javadoc.doclet.DocletEnvironment;

public class DupOk implements Doclet
{
    public static void main(String[] args) {
        File srcFile = new File(System.getProperty("test.src", "."));
        String path1 = new File(srcFile, "sp1").getPath();
        String path2 = new File(srcFile, "sp2").getPath();
        String[] aargs = {
            "-docletpath",
            new File(System.getProperty("test.classes", ".")).getPath(),
            "-doclet",
            "DupOk",
            "-sourcepath",
            path1 + System.getProperty("path.separator") + path2,
            "p"
        };
        // run javadoc on package p
        if (jdk.javadoc.internal.tool.Main.execute(aargs) != 0)
            throw new Error();
    }

    public boolean run(DocletEnvironment root) {
        Set<TypeElement> classes = ElementFilter.typesIn(root.getIncludedElements());
        if (classes.size() != 2)
            throw new Error("1 " + Arrays.asList(classes));
        for (TypeElement clazz : classes) {
            if (getFields(clazz).size() != 1)
                throw new Error("2 " + clazz + " " + getFields(clazz));
        }
        return true;
    }

    List<Element> getFields(TypeElement klass) {
        List<Element> out = new ArrayList<>();
        for (Element e : klass.getEnclosedElements()) {
            if (e.getKind() == ElementKind.FIELD) {
                out.add(e);
            }
        }
        return out;
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

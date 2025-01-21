/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug     6341534
 * @summary PackageElement.getEnclosedElements results in NullPointerException from parse(JavaCompiler.java:429)
 * @author  Steve Sides
 * @author  Peter von der Ahe
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile T6341534.java
 * @compile -proc:only -processor T6341534 dir/package-info.java
 * @compile -processor T6341534 dir/package-info.java
 */

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;
import java.util.*;
import java.util.Set;

public class T6341534 extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> tes, RoundEnvironment renv)  {
        messager.printNote(String.valueOf(eltUtils.getPackageElement("no.such.package")));
        PackageElement dir = eltUtils.getPackageElement("dir");
        messager.printNote(dir.getQualifiedName().toString());
        for (Element e : dir.getEnclosedElements())
            messager.printNote(e.toString());
        return true;
    }
}

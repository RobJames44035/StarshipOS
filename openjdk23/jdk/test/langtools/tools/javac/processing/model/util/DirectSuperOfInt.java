/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug     8034933
 * @summary Types.directSupertypes should return Object as the super type of interfaces
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor DirectSuperOfInt
 * @compile -processor DirectSuperOfInt -proc:only DirectSuperOfInt.java
 */

import java.util.Set;
import java.util.List;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;

public class DirectSuperOfInt extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> tes,
                           RoundEnvironment round) {
        if (round.processingOver())
            return true;

        boolean tested = false;
        for (TypeElement te : typesIn(round.getRootElements())) {
            if (!te.getSimpleName().contentEquals("DirectSuperOfIntI"))
                continue;

            tested = true;
            List<? extends TypeMirror> supers = types.directSupertypes(te.asType());
            if (supers.size() != 1)
                throw new AssertionError("test failed");

            if (!elements.getTypeElement("java.lang.Object").asType().equals((supers.get(0))))
                throw new AssertionError("test failed");
        }
        if (!tested)
            throw new AssertionError("test failed");
        return true;
    }
}

interface DirectSuperOfIntI {}

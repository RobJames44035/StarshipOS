/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6512707
 * @summary "incompatible types" after (unrelated) annotation processing
 * @author  Peter Runge
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile T6512707.java
 * @compile -processor T6512707 TestAnnotation.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.*;

/**
 * Dummy processor to force bug 6512707 to show - it does not matter what
 * the annotation processor does for this bug.
 */
public class T6512707 extends JavacTestingAbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        return false;
    }
}

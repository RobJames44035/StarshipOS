/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug     6359313
 * @summary error compiling annotated package
 * @author  Peter von der Ah\u00e9
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile T6359313.java
 * @compile -processor T6359313 package-info.java Foo.java
 */

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("Foo")
public class T6359313 extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        return true;
    }
}

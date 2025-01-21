/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6453386
 * @summary Test basic properties of javax.lang.element.Element
 * @author  Joseph D. Darcy
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor TestElement
 * @compile -processor TestElement -proc:only TestElement.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;
import static javax.tools.Diagnostic.Kind.*;
import static javax.tools.StandardLocation.*;

/**
 * Test basic workings of javax.lang.element.Element
 */
public class TestElement extends JavacTestingAbstractProcessor {
    /**
     * For now, just check that constructors have a simple name of
     * "<init>".
     */
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            boolean hasRun = false;
            for (Element element : roundEnv.getRootElements()) {
                for (ExecutableElement ctor : constructorsIn(element.getEnclosedElements())) {
                    hasRun = true;
                    Name ctorName = ctor.getSimpleName();
                    if (!ctorName.contentEquals("<init>"))
                        throw new RuntimeException("Unexpected name for constructor " + ctorName);
                }
            }
            if (!hasRun)
                throw new RuntimeException("No constructors!");
        }
        return true;
    }
}

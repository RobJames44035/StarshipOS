/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025118
 * @summary Verify that TypeElement for interfaces does not have
 *          Modifier.DEFAULT in getModifiers()
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor TestTypeElement
 * @compile -processor TestTypeElement -proc:only TestTypeElement.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

/**
 * Verify that TypeElement for interfaces does not have Modifier.DEFAULT in getModifiers().
 */
public class TestTypeElement extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                if (element.getKind().isClass() || element.getKind().isInterface()) {
                    if (element.getModifiers().contains(Modifier.DEFAULT))
                        messager.printError("Modifier.DEFAULT not expected on classes/interfaces");
                }
            }
        }
        return true;
    }

}

/**
 * Test interface to provide a default method.
 */
interface InterfaceWithDefaultMethod {
    default void quux() {}
}

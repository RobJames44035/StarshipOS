/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 8261625
 * @summary Test Elements.isAutomaticModule
 * @library /tools/javac/lib
 * @build   JavacTestingAbstractProcessor TestIsAutomaticMod
 * @compile -processor TestIsAutomaticMod -proc:only TestIsAutomaticMod.java
 */

import java.io.Writer;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;

/**
 * Test basic workings of Elements.isAutomaticModule
 */
public class TestIsAutomaticMod extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            // Named module java.base
            checkMod(eltUtils.getModuleElement("java.base"), false);

            // Unnamed module for TestIsAutomaticMod
            for (Element e : roundEnv.getRootElements() ) {
                ModuleElement enclosing = elements.getModuleOf(e);
                checkMod(enclosing, false);
            }

            if ((new VacuousElements()).isAutomaticModule(null) != false) {
                throw new RuntimeException("Bad behavior from default isAutomaticModule method");
            }
        }
        return true;
    }

    private void checkMod(ModuleElement mod, boolean expectedIsAuto) {
        boolean actualIsAuto = elements.isAutomaticModule(mod);
        if (actualIsAuto != expectedIsAuto) {
            throw new RuntimeException(String.format("Unexpected isAutomatic ``%s''' for %s, expected ``%s''%n",
                                                     actualIsAuto,
                                                     mod,
                                                     expectedIsAuto));
        }
    }
}

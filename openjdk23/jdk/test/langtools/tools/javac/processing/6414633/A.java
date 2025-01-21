/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;

@SuppressWarnings("")
public class A extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager m = processingEnv.getMessager();
        for (TypeElement anno: annotations) {
            for (Element e: roundEnv.getElementsAnnotatedWith(anno))
                m.printError("test", e);

        }
        return true;
    }

    @SuppressWarnings("")
    private void foo() {}
}

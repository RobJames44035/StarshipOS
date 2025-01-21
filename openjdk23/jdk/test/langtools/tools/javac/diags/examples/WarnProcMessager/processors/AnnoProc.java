/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;

@SupportedAnnotationTypes("*")
public class AnnoProc extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> elems, RoundEnvironment renv) {
        if (renv.processingOver()) {
            processingEnv.getMessager().printWarning("Warning!");
        }
        return true;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}

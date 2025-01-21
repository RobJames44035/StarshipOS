/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import java.util.Set;

/* A simple annotation processor. */
@SupportedAnnotationTypes("*")
public class DummyProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public final boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        return false;
    }
}

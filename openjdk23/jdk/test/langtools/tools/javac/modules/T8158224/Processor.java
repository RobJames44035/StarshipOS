/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;

@SupportedAnnotationTypes("Blah")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class Processor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> tE, RoundEnvironment env) {
        return true;
    }
}

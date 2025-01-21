/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("*")
public class Processor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}

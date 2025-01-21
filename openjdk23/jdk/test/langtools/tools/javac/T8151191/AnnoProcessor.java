/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.*;
import javax.lang.model.SourceVersion;

@SupportedAnnotationTypes("*")
public class AnnoProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment re) {
        for (TypeElement tElement : set) {
            Element e = tElement.getEnclosingElement();
            if (e != null) {
                if (e instanceof PackageElement) {
                    ((PackageElement)e).getEnclosedElements();
                }
            }
        }
        return true;
    }
}

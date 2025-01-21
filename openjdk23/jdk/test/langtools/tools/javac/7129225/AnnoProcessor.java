/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("Anno")
public class AnnoProcessor extends JavacTestingAbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment re) {
        messager.printNote("RUNNING - lastRound = " + re.processingOver());
        return true;
    }
}


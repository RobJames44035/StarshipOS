/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;

@SupportedAnnotationTypes("*")
public class AnnoProc extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> elems, RoundEnvironment renv) {
        if (++round == 1) {
            Filer filer = processingEnv.getFiler();
            Messager messager = processingEnv.getMessager();
            try {
                FileObject fo1 = filer.createResource(
                    StandardLocation.CLASS_OUTPUT, "p+q", "Hello-World.txt");
                try (Writer out = fo1.openWriter()) {
                    out.write("Hello World!");
                }
            } catch (IOException e) {
                messager.printError(e.toString());
            }
        }
        return false;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    int round = 0;
}

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
        round++;
        if (round <= 2) {
            Filer filer = processingEnv.getFiler();
            Messager messager = processingEnv.getMessager();
            try {
                JavaFileObject fo = filer.createSourceFile("Gen");
                try (Writer out = fo.openWriter()) {
                    out.write("class Gen { }");
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

    int round;
}

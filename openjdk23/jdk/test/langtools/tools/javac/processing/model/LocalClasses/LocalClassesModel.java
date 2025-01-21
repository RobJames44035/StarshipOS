/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8166700
 * @summary Check that local classes originating in static initializer can be loaded properly.
 * @modules jdk.compiler
 * @library /tools/javac/lib
 * @build LocalTest$1Local LocalTest$2Local LocalTest$3Local LocalTest$4Local LocalTest$5Local LocalTest JavacTestingAbstractProcessor
 * @compile LocalClassesModel.java
 * @compile/process/ref=LocalClassesModel.out -processor LocalClassesModel LocalTest$1Local LocalTest$2Local LocalTest$3Local LocalTest$4Local LocalTest$5Local LocalTest
 */

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

public class LocalClassesModel extends JavacTestingAbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement root : ElementFilter.typesIn(roundEnv.getRootElements())) {
            System.out.println(processingEnv.getElementUtils().getBinaryName(root));
            for (ExecutableElement constr : ElementFilter.constructorsIn(root.getEnclosedElements())) {
                System.out.print("  (");
                boolean first = true;
                for (VariableElement param : constr.getParameters()) {
                    if (!first) {
                        System.out.print(", ");
                    }
                    first = false;
                    System.out.print(param.asType().toString());
                }
                System.out.println(")");
            }
        }

        return false;
    }
}

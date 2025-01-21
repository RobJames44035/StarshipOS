/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @modules jdk.compiler
 * @library /tools/javac/lib
 * @build NestedTypeVars JavacTestingAbstractProcessor
 * @compile/process/ref=NestedTypeVars.out -processor NestedTypeVars Test$1L1$L2$1L3$L4$L5 Test$1L1$CCheck Test$1L1 Test$1CCheck Test$CCheck Test
 */

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.ElementFilter;

public class NestedTypeVars extends JavacTestingAbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement te : ElementFilter.typesIn(roundEnv.getRootElements())) {
            System.out.print(processingEnv.getElementUtils().getBinaryName(te));
            System.out.print("<");
            String separator = "";
            for (TypeParameterElement tp : te.getTypeParameters()) {
                System.out.print(separator);
                separator = ", ";
                System.out.print(tp.getSimpleName());
                System.out.print(" extends ");
                System.out.print(tp.getBounds().stream().map(b -> toString(b)).collect(Collectors.joining("&")));
            }
            System.out.println(">");
            for (ExecutableElement m : ElementFilter.methodsIn(te.getEnclosedElements())) {
                System.out.print("  <");
                separator = "";
                for (TypeParameterElement tp : m.getTypeParameters()) {
                    System.out.print(separator);
                    separator = ", ";
                    System.out.print(tp.getSimpleName());
                    System.out.print(" extends ");
                    System.out.print(tp.getBounds().
                            stream().
                            map(b -> toString(b)).
                            collect(Collectors.joining("&")));
                }
                System.out.print(">");
                System.out.println(m.getSimpleName());
            }
        }

        return false;
    }

    String toString(TypeMirror bound) {
        if (bound.getKind() == TypeKind.TYPEVAR) {
            TypeVariable var = (TypeVariable) bound;
            return toString(var.asElement());
        }
        return bound.toString();
    }

    String toString(Element el) {
        switch (el.getKind()) {
            case METHOD:
                return toString(el.getEnclosingElement()) + "." + el.getSimpleName();
            case CLASS:
                return processingEnv.getElementUtils().getBinaryName((TypeElement) el).toString();
            case TYPE_PARAMETER:
                return toString(((TypeParameterElement) el).getGenericElement()) + "." + el.getSimpleName();
            default:
                throw new IllegalStateException("Unexpected element: " + el + "(" + el.getKind() + ")");
        }
    }
}

class Test<T1, C> {
    <T2, C> void m() {
        class L1<T3, C> {
            class L2<T4, C> {
                <T5, C> void m() {
                    class L3<T6, C> {
                        class L4<T7, C> {
                            class L5<T1a extends T1,
                                     T2a extends T2,
                                     T3a extends T3,
                                     T4a extends T4,
                                     T5a extends T5,
                                     T6a extends T6,
                                     T7a extends T7> {}
                        }
                    }
                }
            }
            class CCheck<T extends C> {}
            <T extends C> void test() {}
        }
        class CCheck<T extends C> {}
    }
    class CCheck<T extends C> {}
    <T extends C> void test() {}
}

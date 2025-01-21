/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6505047
 * @summary javax.lang.model.element.Element.getEnclosingElement() doesn't return null for type parameter
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build JavacTestingAbstractProcessor TestTypeParameter
 * @compile -processor TestTypeParameter -proc:only TestTypeParameter.java
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import javax.tools.*;

public class TestTypeParameter<T> extends JavacTestingAbstractProcessor {
    int round = 0;

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (++round == 1) {
            int found = (new Scanner()).scan(roundEnv.getRootElements(), null);
            if (found == expect) {
                note("generic elements found and verified: " + found);
            } else {
                error("unexpected number of results: expected " + expect
                        + ", found " + found);
            }

        }
        return true;
    }

    class Scanner extends ElementScanner<Integer, Void> {
        @Override
        public Integer visitExecutable(ExecutableElement e, Void p) {
            super.visitExecutable(e, p);
            found += check(e, e.getTypeParameters());
            return found;
        }

        @Override
        public Integer visitType(TypeElement e, Void p) {
            super.visitType(e, p);
            found += check(e, e.getTypeParameters());
            return found;
        }

        int found;
    }

    /**
     * Check if type parameters, if any, have expected owner.
     * Return 1 if typarams not empty and all have expected owner, else return 0.
     */
    int check(Element e, List<? extends TypeParameterElement> typarams) {
        note("checking " + e, e);
        if (typarams.isEmpty()) {
            note("no type parameters found", e);
            return 0;
        }
        for (TypeParameterElement tpe: typarams) {
            note("checking type parameter " + tpe, tpe);
            if (tpe.getEnclosingElement() != e) {
                error("unexpected owner; expected: " + e
                        + ", found " + tpe.getEnclosingElement(),
                        tpe);
                return 0;
            }
            if (tpe.getEnclosingElement() != tpe.getGenericElement()) {
                error("unexpected generic element; expected: " + tpe.getGenericElement()
                        + ", found " + tpe.getEnclosingElement(),
                        tpe);
                return 0;
            }
        }
        note("verified " + e, e);
        return 1;
    }

    void note(String msg) {
        messager.printNote(msg);
    }

    void note(String msg, Element e) {
        messager.printNote(msg, e);
    }

    void error(String msg, Element e) {
        messager.printError(msg, e);
    }

    void error(String msg) {
        messager.printError(msg);
    }

    // additional generic elements to test
    <X> X m(X x) { return x; }

    interface Intf<X> { X m() ; }

    class Class<X> {
        <Y> Class() { }
    }

    final int expect = 5;  // top level class, plus preceding examples
}

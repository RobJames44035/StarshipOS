/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8038455
 * @summary Ensure that Symbols for members (methods and fields) are dropped across annotation
 *          processing rounds. ClassSymbols need to be kept.
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build JavacTestingAbstractProcessor MethodsDroppedBetweenRounds
 * @compile/process -processor MethodsDroppedBetweenRounds MethodsDroppedBetweenRounds.java
 */

import java.lang.ref.WeakReference;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;

public class MethodsDroppedBetweenRounds extends JavacTestingAbstractProcessor {
    private static TypeElement currentClassSymbol;
    private static WeakReference<ExecutableElement> keptMethod = null;
    public boolean process(Set<? extends TypeElement> annos,RoundEnvironment rEnv) {
        if (keptMethod != null) {
            //force GC:
            List<byte[]> hold = new ArrayList<>();
            try {
                while (true)
                    hold.add(new byte[1024 * 1024 * 1024]);
            } catch (OutOfMemoryError err) { }
            hold.clear();
            if (keptMethod.get() != null) {
                throw new IllegalStateException("Holds method across rounds.");
            }
        }

        TypeElement currentClass = elements.getTypeElement("MethodsDroppedBetweenRounds");

        if (currentClassSymbol != null && currentClassSymbol != currentClass) {
            throw new IllegalStateException("Different ClassSymbols across rounds");
        }

        ExecutableElement method = ElementFilter.methodsIn(currentClass.getEnclosedElements()).get(0);

        keptMethod = new WeakReference<>(method);

        return true;
    }
}

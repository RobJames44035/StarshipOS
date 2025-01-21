/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6987384
 * @summary -XprintProcessorRoundsInfo message printed with different timing than previous
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build JavacTestingAbstractProcessor Test
 * @compile/fail/ref=Test.out -XDrawDiagnostics -XprintProcessorInfo -Werror -proc:only -processor Test Test.java
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import javax.tools.*;

public class Test extends JavacTestingAbstractProcessor {
    final int MAX_ROUNDS = 3;
    int round = 0;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        round++;
        messager.printNote("round " + round);
        if (round <= MAX_ROUNDS)
            generateSource("Gen" + round);
        if (roundEnv.processingOver())
            messager.printWarning("last round");
        return true;
    }

    void generateSource(String name) {
        String text = "class " + name + " { }\n";
        try (Writer out = filer.createSourceFile(name).openWriter()) {
            out.write(text);
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}




/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test 6966604
 * @summary JavacFiler not correctly notified of lastRound
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile TestLastRound.java
 * @compile/fail/ref=TestLastRound.out -XDrawDiagnostics -Werror -proc:only -processor TestLastRound TestLastRound.java
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;

public class TestLastRound extends JavacTestingAbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            try {
                JavaFileObject fo = filer.createSourceFile("LastRound.java");
                try (Writer out = fo.openWriter()) {
                    out.write("class LastRound { }");
                }
            } catch (IOException e) {
            }
        }
        return true;
    }
}

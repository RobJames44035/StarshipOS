/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test 6403456
 * @summary -Werror should work with annotation processing
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile WErrorGen.java
 * @compile -proc:only -processor WErrorGen WErrorGen.java
 * @compile/fail/ref=WErrorGen.out -XDrawDiagnostics -Werror -Xlint:rawtypes -processor WErrorGen WErrorGen.java
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;

public class WErrorGen extends JavacTestingAbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (++round == 1) {
            try {
                JavaFileObject fo = filer.createSourceFile("Gen");
                try (Writer out = fo.openWriter()) {
                    out.write("import java.util.*; class Gen { List l; }");
                }
            } catch (IOException e) {
            }
        }
        return true;
    }

    int round = 0;
}

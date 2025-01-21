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
 * @compile WErrorLast.java
 * @compile -proc:only -processor WErrorLast WErrorLast.java
 * @compile/fail/ref=WErrorLast.out -XDrawDiagnostics -Werror -proc:only -processor WErrorLast WErrorLast.java
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;

public class WErrorLast extends JavacTestingAbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            messager.printWarning("last round");
        }
        return true;
    }
}

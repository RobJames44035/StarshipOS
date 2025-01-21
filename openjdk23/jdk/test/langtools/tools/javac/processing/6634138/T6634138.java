/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6634138
 * @author  Joseph D. Darcy
 * @summary Verify source files output after processing is over are compiled
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile T6634138.java
 * @compile -processor T6634138 Dummy.java
 * @run main ExerciseDependency
 */

import java.lang.annotation.Annotation;
import java.io.*;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.*;

public class T6634138 extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        // Write out files *after* processing is over.
        if (roundEnvironment.processingOver()) {
            System.out.println("Writing out source files.");
            try {
                try (PrintWriter pw = new PrintWriter(filer.createSourceFile("foo.WrittenAfterProcessing").openWriter())) {
                     pw.println("package foo;");
                     pw.println("public class WrittenAfterProcessing {");
                     pw.println("  public WrittenAfterProcessing() {super();}");
                     pw.println("}");
                 }

                try (PrintWriter pw = new PrintWriter(filer.createSourceFile("foo.package-info").openWriter())) {
                     pw.println("@Deprecated");
                     pw.println("package foo;");
                 }
            } catch(IOException io) {
                throw new RuntimeException(io);
            }
        }
        return true;
    }
}




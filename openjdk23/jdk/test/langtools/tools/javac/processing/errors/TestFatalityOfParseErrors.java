/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * Write out an incomplete source file and observe that the next round
 * is marked as an error.
 */
public class TestFatalityOfParseErrors extends JavacTestingAbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        try (PrintWriter pw = new PrintWriter(filer.createSourceFile("SyntaxError").openWriter())) {
            pw.println("class SyntaxError {");
        } catch (IOException ioException) {
        }
        return true;
    }
}

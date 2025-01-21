/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/*
 * @test
 * @bug 8060448
 * @summary Test that javac doesn't throw ArrayIndexOutOfBoundsException
 *          when logging the message "\n"
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor NewlineOnlyDiagnostic
 * @compile -processor NewlineOnlyDiagnostic NewlineOnlyDiagnostic.java
 */

public class NewlineOnlyDiagnostic extends JavacTestingAbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> types,RoundEnvironment rEnv) {
        processingEnv.getMessager().printNote("\n");
        return true;
    }
}

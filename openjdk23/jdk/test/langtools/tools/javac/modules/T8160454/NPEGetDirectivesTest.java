/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8160454
 * @summary JSR269 jigsaw update: javax.lang.model.element.ModuleElement.getDirectives() causes NPE on unnamed modules
 * @modules
 *      jdk.compiler/com.sun.tools.javac.code
 *      jdk.compiler/com.sun.tools.javac.util
 * @compile NPEGetDirectivesTest.java
 * @compile -processor NPEGetDirectivesTest NPEGetDirectivesTest.java
 */

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;

import java.util.Set;

import com.sun.tools.javac.code.Directive.RequiresDirective;
import com.sun.tools.javac.code.Symbol.ModuleSymbol;
import com.sun.tools.javac.util.Assert;

import static com.sun.tools.javac.code.Directive.RequiresFlag.MANDATED;

@SupportedAnnotationTypes("*")
public class NPEGetDirectivesTest extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e: roundEnv.getRootElements()) {
            Element m = e.getEnclosingElement();
            while (!(m instanceof ModuleElement)) {
                m = m.getEnclosingElement();
            }
            ((ModuleSymbol)m).getDirectives();
            RequiresDirective requiresDirective = ((ModuleSymbol)m).requires.head;
            Assert.check(requiresDirective.getDependency().getQualifiedName().toString().equals("java.base"));
            Assert.check(requiresDirective.flags.contains(MANDATED));
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}

/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6453386 8216404 8230337
 * @summary Test Elements.getPackageOf
 * @author  Joseph D. Darcy
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor TestGetPackageOf
 * @compile -processor TestGetPackageOf -proc:only TestGetPackageOf.java
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;
import static javax.tools.Diagnostic.Kind.*;
import static javax.tools.StandardLocation.*;

/**
 * Test basic workings of Elements.getPackageOf
 */
public class TestGetPackageOf extends JavacTestingAbstractProcessor {
    /**
     * Check expected behavior on classes and packages and other elements.
     */
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            TypeElement    charElt     = eltUtils.getTypeElement("java.lang.Character");
            PackageElement javaLangPkg = eltUtils.getPackageElement("java.lang");
            PackageElement unnamedPkg  = eltUtils.getPackageElement("");

            Map<Element, PackageElement> testCases =
                Map.of(javaLangPkg, javaLangPkg,
                       charElt,     javaLangPkg,
                       unnamedPkg,  unnamedPkg);

            for (var testCase : testCases.entrySet()) {
                checkPkg(testCase.getKey(), testCase.getValue());
            }

            // The package of fields and methods and nested types of
            // java.lang.Character is java.lang.
            for (Element e : charElt.getEnclosedElements()) {
                checkPkg(e, javaLangPkg);
            }

            // A module has a null package.
            checkPkg(eltUtils.getModuleElement("java.base"), null);
        }
        return true;
    }

    private void checkPkg(Element e, PackageElement expectedPkg) {
        PackageElement actualPkg = eltUtils.getPackageOf(e);
        if (!Objects.equals(actualPkg, expectedPkg)) {
            throw new RuntimeException(String.format("Unexpected package ``%s''' for %s %s, expected ``%s''%n",
                                                     actualPkg, e.getKind(), e.toString(), expectedPkg));
        }
    }
}

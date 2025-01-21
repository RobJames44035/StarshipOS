/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 8230337
 * @summary Test Elements.getModuleOf
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor TestGetModuleOf
 * @compile -processor TestGetModuleOf -proc:only TestGetModuleOf.java
 * @compile -processor TestGetModuleOf -proc:only -source 8 -Xlint:-options TestGetModuleOf.java
 */

// Also run test under -source 8 to test old behavior pre-modules.

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
 * Test basic workings of Elements.getModuleOf
 */
public class TestGetModuleOf extends JavacTestingAbstractProcessor {
    /**
     * Check expected behavior on classes and packages and other elements.
     */
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            TypeElement    charElt     = eltUtils.getTypeElement("java.lang.Character");
            PackageElement javaLangPkg = eltUtils.getPackageElement("java.lang");
            ModuleElement  expectedMod = enclosingToModule(javaLangPkg);

            checkMod(charElt, expectedMod);
            checkMod(javaLangPkg, expectedMod);

            // The module of fields and methods and nested types of
            // java.lang.Character should match the module of
            // java.lang.
            for (Element e : charElt.getEnclosedElements()) {
                checkMod(e, expectedMod);
            }

            // A module of a module is itself
            if (expectedMod != null)
                checkMod(expectedMod, expectedMod);
        }
        return true;
    }

    private ModuleElement enclosingToModule(Element e) {
        Element enclosing = e.getEnclosingElement();
        if (enclosing == null)
            return null;
        else
            return ElementFilter.modulesIn(List.of(enclosing)).get(0);
    }

    private void checkMod(Element e, ModuleElement expectedMod) {
        ModuleElement actualMod = eltUtils.getModuleOf(e);
        if (!Objects.equals(actualMod, expectedMod)) {
            throw new RuntimeException(String.format("Unexpected module ``%s''' for %s %s, expected ``%s''%n",
                                                     actualMod, e.getKind(), e.toString(), expectedMod));
        }
    }
}

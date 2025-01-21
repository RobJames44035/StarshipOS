/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402506 8028545 8028543
 * @summary Test that getSourceVersion works properly
 * @author  Joseph D. Darcy
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor
 * @compile TestSourceVersion.java
 * @compile -processor TestSourceVersion -proc:only -source 1.8 -AExpectedVersion=RELEASE_8 HelloWorld.java
 * @compile -processor TestSourceVersion -proc:only -source   8 -AExpectedVersion=RELEASE_8 HelloWorld.java
 * @compile -processor TestSourceVersion -proc:only -source 1.9 -AExpectedVersion=RELEASE_9 HelloWorld.java
 * @compile -processor TestSourceVersion -proc:only -source   9 -AExpectedVersion=RELEASE_9 HelloWorld.java
 * @compile -processor TestSourceVersion -proc:only -source  10 -AExpectedVersion=RELEASE_10 HelloWorld.java
 * @compile -processor TestSourceVersion -proc:only -source  11 -AExpectedVersion=RELEASE_11 HelloWorld.java
 * @compile -processor TestSourceVersion -proc:only -source  12 -AExpectedVersion=RELEASE_12 HelloWorld.java
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

/**
 * This processor checks that ProcessingEnvironment.getSourceVersion()
 * is consistent with the setting of the -source option.
 */
@SupportedOptions("ExpectedVersion")
public class TestSourceVersion extends JavacTestingAbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        SourceVersion expectedVersion =
            SourceVersion.valueOf(processingEnv.getOptions().get("ExpectedVersion"));
        SourceVersion actualVersion =  processingEnv.getSourceVersion();
        System.out.println("Expected SourceVersion " + expectedVersion +
                           " actual SourceVersion "  + actualVersion);
        if (expectedVersion != actualVersion)
            throw new RuntimeException();

        return true;
    }
}

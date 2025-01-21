/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8161501
 * @summary JSR269 jigsaw update: javax.lang.model.element.ModuleElement.getEnclosedElements() on unnamed module with unnamed package
 * @compile UnnamedModuleUnnamedPackageTest.java
 * @compile -processor UnnamedModuleUnnamedPackageTest UnnamedModuleUnnamedPackageTest.java EmptyClass.java
 */

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;

import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("*")
public class UnnamedModuleUnnamedPackageTest extends AbstractProcessor {
    static final Set<String> expected = new HashSet<>(Arrays.asList("unnamed package", "pkg1"));

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e: roundEnv.getRootElements()) {
            Element m = e.getEnclosingElement();
            while (!(m instanceof ModuleElement)) {
                m = m.getEnclosingElement();
            }
            Set<String> found = m.getEnclosedElements().stream()
                .map(p -> ((PackageElement)p).isUnnamed() ?
                                        "unnamed package" :
                                        ((PackageElement)p).getQualifiedName().toString())
                .collect(Collectors.toSet());
            if (!Objects.equals(expected, found)) {
                System.err.println("expected: " + expected);
                System.err.println("found: " + found);
                throw new AssertionError("unexpected packages found");
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}

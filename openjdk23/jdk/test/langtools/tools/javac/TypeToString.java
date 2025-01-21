/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8309881
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build JavacTestingAbstractProcessor TypeToString
 * @compile -cp . -processor TypeToString -proc:only TypeToString.java
 * @compile/process -cp . -processor TypeToString -proc:only Test
 */
import java.lang.Runtime.Version;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;

@SupportedAnnotationTypes("*")
public class TypeToString extends JavacTestingAbstractProcessor {

    public boolean process(Set<? extends TypeElement> typeElementSet,RoundEnvironment renv) {
        if (renv.processingOver()) {
            TypeElement testClass = processingEnv.getElementUtils().getTypeElement("Test");
            ExecutableElement method = ElementFilter.methodsIn(testClass.getEnclosedElements())
                                                    .iterator()
                                                    .next();
            String expectedTypeToString = "java.lang.Runtime.Version";
            String actualToString = method.getReturnType().toString();

            if (!Objects.equals(expectedTypeToString, actualToString)) {
                throw new AssertionError("Unexpected toString value. " +
                                         "Expected: " + expectedTypeToString + ", " +
                                         "but got: " + actualToString);
            }

            actualToString = method.getParameters().get(0).asType().toString();

            if (!Objects.equals(expectedTypeToString, actualToString)) {
                throw new AssertionError("Unexpected toString value. " +
                                         "Expected: " + expectedTypeToString + ", " +
                                         "but got: " + actualToString);
            }
        }
        return false;
    }
}

class Test {
    public Version get(Version v) {
        return null;
    }
}

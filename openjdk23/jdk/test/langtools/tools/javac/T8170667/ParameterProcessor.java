/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import static java.util.stream.Collectors.toCollection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/*
 * @test
 * @bug 8170667
 * @summary ClassReader assigns method parameters from MethodParameters incorrectly when long/double
 * parameters are present
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @compile -parameters ParameterProcessor.java
 * @compile/process -proc:only -processor ParameterProcessor ParameterProcessor
 */
@SupportedAnnotationTypes("ParameterProcessor.ParameterNames")
public class ParameterProcessor extends JavacTestingAbstractProcessor {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ParameterNames {
        String[] value() default {};
    }

    @ParameterProcessor.ParameterNames({"a", "b", "c"})
    void f(int a, int b, int c) {}

    @ParameterProcessor.ParameterNames({"d", "e", "f"})
    void g(int d, long e, int f) {}

    @ParameterProcessor.ParameterNames({"g", "h", "i", "j"})
    void h(int g, double h, int i, int j) {}

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ParameterNames.class)) {
            ParameterNames names = element.getAnnotation(ParameterNames.class);
            if (names == null) {
                continue;
            }
            List<String> expected = Arrays.asList(names.value());
            List<String> actual =
                    ((ExecutableElement) element)
                            .getParameters()
                            .stream()
                            .map(p -> p.getSimpleName().toString())
                            .collect(toCollection(ArrayList::new));
            if (!expected.equals(actual)) {
                String message =
                        String.format(
                                "bad parameter names for %s#%s; expected: %s, was: %s",
                                element, element, expected, actual);
                messager.printError(message);
            }
        }
        return false;
    }
}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026855
 * @summary Javac should only look on supertypes for repeatable annotations if
 *          both container and containee are inherited.
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.util
 * @build   JavacTestingAbstractProcessor TestNonInherited
 * @compile -XDaccessInternalAPI -processor TestNonInherited -proc:only TestNonInherited.java
 */

import com.sun.tools.javac.util.Assert;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

import static javax.lang.model.util.ElementFilter.*;

@TestNonInherited.Foo(1)
public class TestNonInherited extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            boolean hasRun = false;
            for (Element element : roundEnv.getRootElements())
                for (TypeElement te : typesIn(element.getEnclosedElements()))
                    if (te.getQualifiedName().contentEquals("TestNonInherited.T2")) {
                        hasRun = true;
                        Foo[] foos = te.getAnnotationsByType(Foo.class);
                        System.out.println("  " + te);
                        System.out.println("  " + Arrays.asList(foos));
                        Assert.check(foos.length == 0, "Should not find any instance of @Foo");
                    }
            if (!hasRun)
                throw new RuntimeException("The annotation processor could not find the declaration of T2, test broken!");
        }
        return true;
    }

    public static class T2 extends TestNonInherited {
    }

    @Repeatable(FooContainer.class)
    public static @interface Foo {
        int value();
    }

    @Inherited
    public static @interface FooContainer {
        Foo[] value();
    }
}

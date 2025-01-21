/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @summary test that order is respected when inheriting both legacy container and single anno
 * @bug 8007961
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.util
 * @build   JavacTestingAbstractProcessor EnsureOrder
 * @compile -XDaccessInternalAPI -processor EnsureOrder -proc:only EnsureOrder.java
 */

import java.util.Set;
import java.lang.annotation.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import com.sun.tools.javac.util.Assert;

@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Foos.class)
@interface Foo {
    int value();
}

@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface Foos {
    Foo[] value();
}

@Foos({@Foo(0), @Foo(1)}) @Foo(2)
class Base {}

class Sub extends Base {}

public class EnsureOrder<@Foos({@Foo(0), @Foo(1)}) @Foo(2)T> extends JavacTestingAbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            int hasRun = 0;
            for (Element element : roundEnv.getRootElements()) {
                Name elemName = element.getSimpleName();
                if (elemName.contentEquals("Base")) {
                    hasRun++;
                    Foo[] foos = element.getAnnotationsByType(Foo.class);
                    Assert.check(foos.length == 3);
                    Assert.check(foos[0].value() == 0);
                    Assert.check(foos[1].value() == 1);
                    Assert.check(foos[2].value() == 2);
                }
                if (elemName.contentEquals("Sub")) {
                    hasRun++;
                    Foo[] foos = element.getAnnotationsByType(Foo.class);
                    Assert.check(foos.length == 3);
                    Assert.check(foos[0].value() == 0);
                    Assert.check(foos[1].value() == 1);
                    Assert.check(foos[2].value() == 2);
                }
                if (elemName.contentEquals("EnsureOrder")) {
                    for (TypeParameterElement t : ((TypeElement)element).getTypeParameters()) {
                        if (t.getSimpleName().contentEquals("T")) {
                            hasRun++;
                            Foo[] foos = t.getAnnotationsByType(Foo.class);
                            Assert.check(foos.length == 3);
                            Assert.check(foos[0].value() == 0);
                            Assert.check(foos[1].value() == 1);
                            Assert.check(foos[2].value() == 2);
                        }
                    }
                }
            }
            if (hasRun != 3)
                throw new RuntimeException("Couldn't find elements");
        }
        return true;
    }
}

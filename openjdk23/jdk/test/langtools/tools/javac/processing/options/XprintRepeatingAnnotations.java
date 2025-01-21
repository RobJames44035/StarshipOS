/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 8005295
 * @summary Verify repeating annotations are printed as expected
 * @compile/ref=XprintRepeatingAnnotations.out -Xprint  XprintRepeatingAnnotations.java
 */

import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;

@Foo(1)
@Foo(2)
@Bar(3)
@Bar(4)
public class XprintRepeatingAnnotations {
}

@Retention(RUNTIME)
@Documented
@Repeatable(Foos.class)
@interface Foo {
    int value();
}

@Retention(RUNTIME)
@Documented
@interface Foos {
    Foo[] value();
}

@Retention(RUNTIME)
@Documented
@Repeatable(Bars.class)
@interface Bar {
    int value();
}

@Retention(RUNTIME)
@Documented
@interface Bars {
    Bar[] value();
    int quux() default 1;
}

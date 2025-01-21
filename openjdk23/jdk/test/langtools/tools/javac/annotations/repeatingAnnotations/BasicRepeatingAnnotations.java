/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @summary Smoke test for repeating annotations
 * @bug 7151010
 *
 * @run clean BasicRepeatingAnnotations BasicRepeatingAnnos BasicNonRepeatingAnno Foo Foos Bar
 * @run compile BasicRepeatingAnnotations.java
 * @run main BasicRepeatingAnnotations
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Foos.class)
@interface Foo {}

@Retention(RetentionPolicy.RUNTIME)
@interface Foos {
    Foo[] value();
}

@interface Bar {}

@Foo @Foo
@Foo
@Bar
@Foo
@Foo
@Foo
@Foo
@Foo @Foo
@Foo
class BasicRepeatingAnnos {}

@Foo
class BasicNonRepeatingAnno {}

public class BasicRepeatingAnnotations {
    public static void main(String[] args) throws Exception {
        Annotation a = BasicRepeatingAnnos.class.getAnnotation(Foos.class);
        if (a == null) {
            throw new RuntimeException("Container annotation missing");
        }

        // verify that container not present on nonrepeating
        a = BasicNonRepeatingAnno.class.getAnnotation(Foos.class);
        if (a != null) {
            throw new RuntimeException("Container annotation present");
        }
        a = BasicNonRepeatingAnno.class.getAnnotation(Foo.class);
        if (a == null) {
            throw new RuntimeException("Repeated annotation not directly present");
        }
    }
}

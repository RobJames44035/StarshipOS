/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @summary Smoke test for repeating annotations
 * @bug 7151010
 *
 * @run clean NestedContainers BasicRepeatingAnnos BasicRepeatingAnnos2 Foo Foos FoosFoos
 * @run compile NestedContainers.java
 * @run main NestedContainers
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Foos.class)
@interface Foo {}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FoosFoos.class)
@interface Foos {
    Foo[] value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface FoosFoos {
    Foos[] value();
}

@Foo
@Foo
class BasicRepeatingAnnos {}

@Foos({})
@Foos({})
class BasicRepeatingAnnos2 {}

public class NestedContainers {
    public static void main(String[] args) throws Exception {
        Annotation a = BasicRepeatingAnnos.class.getAnnotation(Foos.class);
        if (a == null) {
            throw new RuntimeException("Container annotation missing");
        }

        // Check 2:nd level container
        a = BasicRepeatingAnnos2.class.getAnnotation(FoosFoos.class);
        if (a == null) {
            throw new RuntimeException("Container annotation missing");
        }
    }
}

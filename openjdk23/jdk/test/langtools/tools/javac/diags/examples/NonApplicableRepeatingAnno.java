/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// key: compiler.err.invalid.repeatable.annotation.not.applicable

import java.lang.annotation.*;

@Repeatable(Foos.class)
@interface Foo {}

@Target(ElementType.ANNOTATION_TYPE)
@interface Foos {
    Foo[] value();
}

public class NonApplicableRepeatingAnno {
    @Foo @Foo int f = 0;
}

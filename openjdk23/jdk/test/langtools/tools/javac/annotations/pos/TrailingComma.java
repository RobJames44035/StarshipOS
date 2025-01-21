/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6337964
 * @summary javac incorrectly disallows trailing comma in annotation arrays
 * @author darcy
 * @compile TrailingComma.java
 */

import java.lang.annotation.*;

@interface TestAnnotation {
    SuppressWarnings[] value() default {@SuppressWarnings({"",})};
}


@TestAnnotation({@SuppressWarnings({}),
                 @SuppressWarnings({"Beware the ides of March.",}),
                 @SuppressWarnings({"Look both ways", "Before Crossing",}), })
public class TrailingComma {
}

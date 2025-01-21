/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug     5037685
 * @summary Under certain circumstances, recursive annotations disappeared
 * @author  Josh Bloch
 */

import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;

@Rat public class RecursiveAnnotation {
    public static void main(String[] args) {
        if (!RecursiveAnnotation.class.isAnnotationPresent(Rat.class))
            throw new RuntimeException("RecursiveAnnotation");

        if (!Rat.class.isAnnotationPresent(Rat.class))
            throw new RuntimeException("Rat");
    }
}

@Retention(RUNTIME) @Rat @interface Rat { }

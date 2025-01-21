/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug     7021922
 * @summary Test null handling of IncompleteAnnotationException constructor
 * @author  Joseph D. Darcy
 */

import java.lang.annotation.*;

public class TestIncompleteAnnotationExceptionNPE {
    public static void main(String... args) {
        int errors = 0;
        Class<? extends Annotation> annotationType = Annotation.class;
        String elementName = "name";

        try {
            Object o = new IncompleteAnnotationException(null, null);
            errors++;
        } catch(NullPointerException npe) {
            ; // Expected
        }

        try {
            Object o = new IncompleteAnnotationException(annotationType, null);
            errors++;
        } catch(NullPointerException npe) {
            ; // Expected
        }

        try {
            Object o = new IncompleteAnnotationException(null, elementName);
            errors++;
        } catch(NullPointerException npe) {
            ; // Expected
        }

        if (errors != 0)
            throw new RuntimeException("Encountered " + errors +
                                       " error(s) during construction.");
    }
}

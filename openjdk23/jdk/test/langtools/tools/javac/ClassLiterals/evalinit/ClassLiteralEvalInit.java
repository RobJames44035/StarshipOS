/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4256830
 * @summary Check that references to TYPE fields generated during the translation
 * of class literals are handled correctly even if the wrapper classes haven't yet
 * been compiled.
 *
 * @compile ClassLiteralEvalInit.java
 */

public class ClassLiteralEvalInit {
    Class foo = int.class;
}

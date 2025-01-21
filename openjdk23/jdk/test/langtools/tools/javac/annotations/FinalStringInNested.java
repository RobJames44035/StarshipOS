/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8054448
 * @summary Verify that constant strings in nested classes in anonymous classes
 *          can be used in annotations.
 * @compile FinalStringInNested.java
 */

public class FinalStringInNested {

    public void f() {
        Object o = new Object() {
            @FinalStringInNested.Annotation(Nested.ID)
            class Nested {
                static final String ID = "B";
            }
        };
    }

    @interface Annotation {
        String value();
    }
}

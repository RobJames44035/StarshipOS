/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6318240
 * @summary Creation of array of inner class of an enclosing wildcard type doesn't work
 * @compile Bar.java
 */

class Bar<T> {
    Bar<?>.Inner<?>.InnerMost[] array = new Bar<?>.Inner<?>.InnerMost[10];
    class Inner<S> {
        class InnerMost {
        }
    }
}

/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4396418
 * @summary Empty array initializer with comma
 * @author gafter
 *
 * @compile EmptyArray.java
 */

class EmptyArray {
    int[] i = {,};
    int[] j = new int[] {,};
}

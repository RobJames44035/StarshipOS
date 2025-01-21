/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4069861
 * @summary The compiler got a null pointer exception on code like the
 *          following.
 * @compile ConditionalInline.java
 * @author turnidge
 */
public class ConditionalInline {
    void method(int i) {
        boolean a;
        if ((i < 7) ? a = true : false) {
        }
    }
}

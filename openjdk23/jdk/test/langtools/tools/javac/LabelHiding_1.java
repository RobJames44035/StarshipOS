/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4309177
 * @summary Verify that label hiding check allows label in nested class to hide outer label.
 * @author maddox
 *
 * @run compile LabelHiding_1.java
 */

class LabelHiding_1 {

    { i:
         {
           class Nested {
             { i: ; }
           }
         }
    }

}

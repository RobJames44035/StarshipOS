/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5028351
 * @summary int.class and ilk have wrong type (5028351 rejected)
 * @author gafter
 *
 * @compile  PrimitiveClass.java
 */

package primitive._class;

class PrimitiveClass {
    Class<Integer> ci = int.class;
}

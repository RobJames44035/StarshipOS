/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4312127
 * @summary Verify that forward reference checks are not applied to locals.
 * @author William Maddox (maddox)
 *
 * @run compile ForwardReference_5.java
 */

class ForwardReference_5 {

  void test() {

    int i = (i = 1) + i++; //legal (i in scope and definitely assigned)

  }

}

/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4312145 4307810
 * @summary Verify that forward referenced static use is rejected.
 * @author William Maddox (maddox)
 *
 * @run compile/fail ForwardReference_2.java
 */

class ForwardReference_2 {

  static {
    System.out.println(x);  //illegal forward reference
  }

  static int x = 1;

}

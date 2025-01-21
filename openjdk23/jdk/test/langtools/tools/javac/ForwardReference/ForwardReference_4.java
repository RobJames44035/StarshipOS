/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4312145 4307810
 * @summary Verify that forward referenced non-static use is rejected.
 * @author William Maddox (maddox)
 *
 * @run compile/fail ForwardReference_4.java
 */

class ForwardReference_4 {

  {
    System.out.println(x);  //illegal forward reference
  }

  int x = 1;

}

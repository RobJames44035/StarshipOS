/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4278953 4713248
 * @summary Verify that access to member of outer class from within anonymous
 * class in 'super()' call is allowed.
 * @author maddox (cribbed from bracha/lillibridge) gafter
 *
 * @compile UplevelFromAnonInSuperCall.java
 */

class UplevelFromAnonInSuperCall {
  int x;
  class Dummy {
     Dummy(Object o) {}
  }
  class Inside extends Dummy {
    Inside() {
       super(new Object() { int r = x; });
    }
  }
}

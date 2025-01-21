/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4309176
 * @summary Verify exception thrown for null outer instance
 * in qualifed new instance expression.
 * @author William Maddox, Gilad Bracha
 *
 * @run compile NullQualifiedNew.java
 * @run main/fail NullQualifiedNew
 */

public class NullQualifiedNew {

  class Nested {
    int j;
    Nested(int i){ j = i;}
  }

  public static void main(String[] args) {
    NullQualifiedNew e = null;
    e.new Nested(6);
  }

}

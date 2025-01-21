/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

// WARNING: this file much be compiled first before DeprecatedDocCommentTest.java
// in test/tools/javac/ is compiled. This is because the compiler *does not*
// print deprecation warnings for a file currently being compiled.

// If this file fails to compile, then the test has failed.  The test does not
// need to be run.

//package depDocComment ;

public class DeprecatedDocComment2 {

  public static void main(String argv[]) {

    // should just skip over this one
    System.out.println("Hello World");

    /* and this one too */
    System.out.println("Hello World");

  }

    /**
     * @deprecated The compiler should print out deprecation warning for this
   * function
   */
    public static void deprecatedTest1() {
    System.out.println("1");
  }

  /*
   * @deprecated The compiler should not print deprecation warning since this
   * is not a legal docComment
   */
  public static void deprecatedTest2() {
    System.out.println("1");
  }

  /*
   * @deprecated Nor this one */
  public static void deprecatedTest3() {
    System.out.println("1");
  }

  /* @deprecated Nor this */
  public static void deprecatedTest4() {
    System.out.println("1");
  }

  /** @deprecated But it should for this */
  public static void deprecatedTest5() {
    System.out.println("1");
  }

  /**@deprecated But it should for this*/
  public static void deprecatedTest6() {
    System.out.println("1");
  }

    /*
     @deprecated But not for this
     */
    public static void deprecatedTest7() {
        System.out.println("1");
    }



    /**
     * not at the beginning of line @deprecated But not for this
     */
    public static void deprecatedTest8() {
        System.out.println("1");
    }

}

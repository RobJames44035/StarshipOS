/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7073477
 * @summary NPE in com.sun.tools.javac.code.Symbol$VarSymbol.getConstValue
 * @compile T7073477.java
 */

@SuppressWarnings(T7073477A.S)
class T7073477 {
}

class T7073477A {
  @SuppressWarnings("")
  static final String S = "";
}


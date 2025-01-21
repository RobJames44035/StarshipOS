/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class InvalidLocation {
  void test() {
    String[] s = new String @A [5] ;
  }
}

@java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE)
@interface A { }

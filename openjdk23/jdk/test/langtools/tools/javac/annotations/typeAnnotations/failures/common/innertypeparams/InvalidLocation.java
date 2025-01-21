/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class InvalidLocation {
  void innermethod() {
    class Inner<@A K> {}
  }
}

@java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE)
@interface A { }

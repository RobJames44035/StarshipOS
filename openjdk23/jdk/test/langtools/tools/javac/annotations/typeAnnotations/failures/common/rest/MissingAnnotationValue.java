/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

class MissingAnnotationValue {
  void test() {
    new @A String();
  }
}

@Target(ElementType.TYPE_USE)
@interface A { int field(); }

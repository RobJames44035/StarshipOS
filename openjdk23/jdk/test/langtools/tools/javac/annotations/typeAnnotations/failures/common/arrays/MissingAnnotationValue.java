/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;

class MissingAnnotationValue {
  void test() {
    String @A [] s;
  }
}

@Target(ElementType.TYPE_USE)
@interface A { int field(); }

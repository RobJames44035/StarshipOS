/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;
class DuplicateAnnotationValue {
  void test() {
    new @A String();
  }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { int field(); }

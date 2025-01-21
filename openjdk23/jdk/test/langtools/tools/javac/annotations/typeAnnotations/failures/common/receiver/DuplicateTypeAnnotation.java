/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;
class DuplicateTypeAnnotation {
  void test(@A @A DuplicateTypeAnnotation this) { }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { }

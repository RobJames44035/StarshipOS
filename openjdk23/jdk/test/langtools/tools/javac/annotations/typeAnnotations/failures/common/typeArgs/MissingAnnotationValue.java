/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;

class MissingAnnotationValue<K> {
  MissingAnnotationValue<@A String> l;
}

@Target(ElementType.TYPE_USE)
@interface A { int field(); }

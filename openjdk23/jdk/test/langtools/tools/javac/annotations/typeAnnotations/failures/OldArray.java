/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;

class OldArray {
  String [@A]  s() { return null; }
}

@Target(ElementType.TYPE_USE)
@interface A { }

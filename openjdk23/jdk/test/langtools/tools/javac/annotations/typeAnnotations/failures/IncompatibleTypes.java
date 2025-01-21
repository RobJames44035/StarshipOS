/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.lang.annotation.*;
import java.util.List;

class IncompatibleTypes {
  List<@A Number> f(List<String> xs) {
    return xs;
  }
}

@Target(ElementType.TYPE_USE)
@interface A { }

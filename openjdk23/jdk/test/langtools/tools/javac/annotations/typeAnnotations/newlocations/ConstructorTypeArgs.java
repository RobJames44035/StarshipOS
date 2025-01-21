/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.lang.annotation.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary new type annotation location: constructor type args
 * @author Mahmood Ali
 * @compile ConstructorTypeArgs.java
 */

class ConstructorTypeArgs {
  void oneArg() {
    new @A MyList<@A String>();
    new MyList<@A MyList<@B(0) String>>();
  }

  void twoArg() {
    new MyMap<String, String>();
    new MyMap<@A String, @B(0) MyList<@A String>>();
  }

  void withArraysIn() {
    new MyList<String[]>();
    new MyList<@A String @B(0) [] @A []>();

    new MyMap<@A String[], @B(0) MyList<@A String> @A []>();
  }
}

class MyList<E> { }
class MyMap<K, V> { }

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { }
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface B { int value(); }

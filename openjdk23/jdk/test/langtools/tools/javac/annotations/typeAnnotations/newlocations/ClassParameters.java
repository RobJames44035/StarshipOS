/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.lang.annotation.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary new type annotation location: class type parameter bounds
 * @author Mahmood Ali
 * @compile ClassParameters.java
 */
class Unannotated<K> { }

class ExtendsBound<K extends @A String> { }
class ExtendsGeneric<K extends @A Unannotated<@B String>> { }
class TwoBounds<K extends @A String, V extends @B String> { }

class Complex1<K extends @A String&Runnable> { }
class Complex2<K extends String & @B Runnable> { }
class ComplexBoth<K extends @A String & @A Runnable> { }

class Outer {
  void inner() {
    class Unannotated<K> { }

    class ExtendsBound<K extends @A String> { }
    class ExtendsGeneric<K extends @A Unannotated<@B String>> { }
    class TwoBounds<K extends @A String, V extends @B String> { }

    class Complex1<K extends @A String&Runnable> { }
    class Complex2<K extends String & @B Runnable> { }
    class ComplexBoth<K extends @A String & @A Runnable> { }
  }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { }
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface B { }

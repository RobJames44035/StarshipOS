/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.lang.annotation.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary new type annotation location: class extends/implements
 * @author Mahmood Ali
 * @compile ClassExtends.java
 */
abstract class MyClass extends @A ParameterizedClass<@B String>
  implements @B CharSequence, @A ParameterizedInterface<@B String> { }

interface MyInterface extends @A ParameterizedInterface<@A String>,
                              @B CharSequence { }

class ParameterizedClass<K> {}
interface ParameterizedInterface<K> {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A {}
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface B {}

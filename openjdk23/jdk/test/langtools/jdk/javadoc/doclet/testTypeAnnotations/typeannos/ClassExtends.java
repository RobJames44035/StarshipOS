/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package typeannos;

import java.lang.annotation.*;

/*
 * This class is replicated from test/tools/javac/annotations/typeAnnotations/newlocations.
 */
abstract class MyClass extends @ClassExtA ParameterizedClass<@ClassExtB String>
  implements @ClassExtB CharSequence, @ClassExtA ParameterizedInterface<@ClassExtB String> { }

interface MyInterface extends @ClassExtA ParameterizedInterface<@ClassExtA String>,
                              @ClassExtB CharSequence { }

class ParameterizedClass<K> {}
interface ParameterizedInterface<K> {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Documented
@interface ClassExtA {}
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Documented
@interface ClassExtB {}

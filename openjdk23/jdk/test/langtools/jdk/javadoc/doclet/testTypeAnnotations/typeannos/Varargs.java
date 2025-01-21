/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package typeannos;

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Documented
@interface VarArgA {}

/*
 * This class is replicated from test/tools/javac/annotations/typeAnnotations/newlocations.
 */
class Varargs {

    // Handle annotations on a varargs element type
    void varargPlain(Object @VarArgA... objs) {
    }

    void varargGeneric(Class<?> @VarArgA ... clz) {
    }
}

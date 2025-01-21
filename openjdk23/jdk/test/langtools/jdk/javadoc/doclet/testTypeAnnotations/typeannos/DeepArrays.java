/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package typeannos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class DeepArrays {
    @ArrA String @ArrB [] @ArrC [] array2() { return null; }
    String @ArrA [] @ArrB [] @ArrC [] @ArrD [] array4() { return null; }
    @ArrA ArrParameterized<@ArrC String @ArrA [] @ArrB []> @ArrC [] @ArrD [] manyNested() { return null; }
    void varargs(@ArrA String @ArrB [] @ArrC [] @ArrD ... arg) {}
    int @ArrA [] mixedStyles(int @ArrB [] @ArrA [] arg) @ArrB [] { return null; } // JLS example 10.2-2
}

class ArrParameterized<T> {}

@Target(ElementType.TYPE_USE)
@Documented
@interface ArrA { }
@Target(ElementType.TYPE_USE)
@Documented
@interface ArrB { }
@Target(ElementType.TYPE_USE)
@Documented
@interface ArrC { }
@Target(ElementType.TYPE_USE)
@Documented
@interface ArrD { }

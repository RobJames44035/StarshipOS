/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * Test that an auxiliary class referenced from its own source file,
 * does not trigger the warning. Such code does not prevent implicit
 * compilation. Also test that references to inner classes do not trigger the warning.
 */

/*
 * @test
 * @bug 7153951
 * @run compile -Werror -Xlint:auxiliaryclass SelfClassWithAux.java ClassWithAuxiliary.java
 * @run compile -Werror -Xlint:auxiliaryclass SelfClassWithAux.java
 */

class SelfClassWithAux {
    AuxClass aux;
    ClassWithAuxiliary.NotAnAuxiliaryClass alfa;
    ClassWithAuxiliary.NotAnAuxiliaryClassEither beta;
}

class AuxClass {
    AuxClass aux;
}

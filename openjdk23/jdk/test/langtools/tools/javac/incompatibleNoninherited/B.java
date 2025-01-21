/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

interface B {
    int f();
}

/** C is legal because no two *inherited* methods conflict. a.A.f() is
 *  not inherited because it is package private in another package. */
abstract class C extends a.A implements B {
}

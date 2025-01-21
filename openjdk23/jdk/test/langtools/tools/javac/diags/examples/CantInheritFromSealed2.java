/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.non.sealed.or.sealed.expected

sealed interface SealedInterface permits Sub1 {
    void m();
}
interface Sub1 extends SealedInterface {}

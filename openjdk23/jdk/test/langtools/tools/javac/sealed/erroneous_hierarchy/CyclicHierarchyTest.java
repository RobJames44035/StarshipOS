/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class CyclicHierarchyTest {
    sealed interface Action permits Add {}
    sealed interface MathOp permits Add {}
    sealed static class Add implements MathOp permits Add {}
}

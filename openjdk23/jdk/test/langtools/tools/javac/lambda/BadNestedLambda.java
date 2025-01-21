/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class BadNestedLambda {
    void test() {
        Runnable add = (int x) -> (int y) -> x + y;
    }
}

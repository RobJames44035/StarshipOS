/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

interface MathOperation {
    public int operation(int a, int b);
}

class Xost {
    static class B {
        public static int operate(int a, int b, MathOperation mathOperation) {
            return mathOperation.operation(a, b);
        }

        static int test_math() {
            MathOperation subtraction = (int a, int b) -> a - b;
            return operate(10, 5, subtraction);
        }
    }
}

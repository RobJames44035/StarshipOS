/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.Comparator;

public class LambdaTest1_neg1 {
    void method() {
        Comparator<Number> c = (Number n1, Number n2) -> { 42; } //compile error, not a statement
    }
}

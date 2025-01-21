/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class T8329115 {
    record R1() {}
    record R2() {}

    int test() {
        return switch (new R1()) {
            case R1() -> {
                return switch (new R2()) { // crashes, instead it should just be the error: attempt to return out of a switch expression
                    case R2() -> 1;
                };
            }
        };
    }
}

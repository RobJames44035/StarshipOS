/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class T8314423 {
    record R1() {}
    record R2() {}

    static void test(Object obj) {
        switch (obj) {
            case R1(), R2() -> System.out.println("R1 or R2");
            default -> System.out.println("other");
        }
    }

    public static void main(String[] args) {
        test(new R1());
    }
}

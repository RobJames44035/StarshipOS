/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8333766
 * @summary Test for compiler crash when anonymous class created in early lambda
 */

public class AnonSuperLambdaCrash {
    class Inner {
        Inner() {
            this(() -> new Object() { { AnonSuperLambdaCrash.this.hashCode(); } });
        }
        Inner(Runnable r) {
            r.run();
        }
    }

    public static void main(String[] args) {
        new AnonSuperLambdaCrash().new Inner();
    }
}

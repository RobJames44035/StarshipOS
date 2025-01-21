/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test 8207320
 * @summary Wrong type order for intersection lambdas with multiple abstract methods
 */

public class IntersectionOrderTest {
    interface A {
        void a();
    }

    interface AB extends A {
        default void a() {
            System.out.println("A");
        }
        default void b() {
            System.out.println("B");
        }
    }

    public static void main(String... args) {
        Object o = new AB() {};
        Runnable r = ((A & AB) o)::b;
    }
}

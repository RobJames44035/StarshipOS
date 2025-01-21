/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7003595
 * @summary IncompatibleClassChangeError with unreferenced local class with subclass
 */

public class T7003595b {
    public static void main(String... args) throws Exception {
        class A {}
        class B extends A {}
        B.class.getSuperclass().getDeclaringClass();
    }
}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8007736
 * @summary Test static interface method.
 * @run main/othervm -Xverify:all TestStaticIF
 */

public class TestStaticIF implements StaticMethodInInterface {

    public static void main(String[] args) {
        System.out.printf("main: %s%n", StaticMethodInInterface.get());
    }
}

interface StaticMethodInInterface {

    public static String get() {
        return "Hello from StaticMethodInInterface.get()";
    }
}

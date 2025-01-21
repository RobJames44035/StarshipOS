/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8013418
 * @summary [JDK 8] Test correct handling of static public interface methods
 * @run main/othervm -Xverify:all PublicStaticInterfaceMethodHandling
 */

class TestClass implements InterfaceWithStaticAndDefaultMethods {
}

interface InterfaceWithStaticAndDefaultMethods {
    public static String get() {
        return "Hello from StaticMethodInInterface.get()";
    }
    default void default_method() {
        System.out.println("Default method FunctionalInterface:default_method()");
    }
}

public class PublicStaticInterfaceMethodHandling  {
    public static void main(String[] args) {
        TestClass tc = new TestClass();
        tc.default_method();
    }
}

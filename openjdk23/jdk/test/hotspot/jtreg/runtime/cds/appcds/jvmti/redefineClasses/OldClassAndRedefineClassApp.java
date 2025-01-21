/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

public class OldClassAndRedefineClassApp {

    public static void main(String args[]) throws Throwable {
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();

        System.out.println("Main: loading OldSuper");
        // Load an old class (version 49), but not linking it.
        Class.forName("OldSuper", false, appClassLoader);

        // Redefine a class unrelated to the above old class.
        System.out.println("INFO: instrumentation = " + RedefineClassHelper.instrumentation);
        Class<?> c = Class.forName("Hello", false, appClassLoader);
        byte[] bytes = c.getClassLoader().getResourceAsStream(c.getName().replace('.', '/') + ".class").readAllBytes();
        RedefineClassHelper.redefineClass(c, bytes);

        System.out.println("Main: loading ChildOldSuper");
        // Load and link a subclass of the above old class.
        // This will in turn link the old class and initializes its vtable, etc.
        Class.forName("ChildOldSuper");
    }
}

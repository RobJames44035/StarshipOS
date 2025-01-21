/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6639645
 * @summary Modeling type implementing missing interfaces
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @clean MissingGenericInterface2
 * @build JavacTestingAbstractProcessor Generator
 * @compile -XprintRounds -processor Generator TestMissingGenericInterface2.java
 * @run main TestMissingGenericInterface2
 */

import java.util.*;

public class TestMissingGenericInterface2 implements MissingGenericInterface2<Integer,String> {
    public static void main(String... args) {
        new TestMissingGenericInterface2().run();
    }

    @Override
    public void run() {
        Class<?> c = getClass();
        System.out.println("class: " + c);
        System.out.println("superclass: " + c.getSuperclass());
        System.out.println("generic superclass: " +c.getGenericSuperclass());
        System.out.println("interfaces: " + Arrays.asList(c.getInterfaces()));
        System.out.println("generic interfaces: " + Arrays.asList(c.getGenericInterfaces()));
    }

}

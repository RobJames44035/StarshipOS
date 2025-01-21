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
 * @clean MissingGenericClass1
 * @build JavacTestingAbstractProcessor Generator
 * @compile -XprintRounds -processor Generator TestMissingGenericClass1.java
 * @run main TestMissingGenericClass1
 */

public class TestMissingGenericClass1 extends MissingGenericClass1<String> {
    public static void main(String... args) {
        new TestMissingGenericClass1().run();
    }
}

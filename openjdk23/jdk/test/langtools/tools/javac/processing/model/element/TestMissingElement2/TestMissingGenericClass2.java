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
 * @clean MissingGenericClass2
 * @build JavacTestingAbstractProcessor Generator
 * @compile -XprintRounds -processor Generator TestMissingGenericClass2.java
 * @run main TestMissingGenericClass2
 */

public class TestMissingGenericClass2 extends MissingGenericClass2<String,Integer> {
    public static void main(String... args) {
        new TestMissingGenericClass2().run();
    }
}

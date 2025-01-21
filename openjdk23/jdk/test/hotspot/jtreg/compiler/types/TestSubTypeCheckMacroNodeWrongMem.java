/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8239367
 * @summary Wiring of memory in SubTypeCheck macro node causes graph should be schedulable
 *
 * @run main/othervm -Xcomp -XX:CompileOnly=TestSubTypeCheckMacroNodeWrongMem::test -XX:+IgnoreUnrecognizedVMOptions -XX:-DoEscapeAnalysis TestSubTypeCheckMacroNodeWrongMem
 *
 */

public class TestSubTypeCheckMacroNodeWrongMem {
    private static int stop = 100;

    public static void main(String[] args) {
        TestSubTypeCheckMacroNodeWrongMem o = new TestSubTypeCheckMacroNodeWrongMem();
        test();
    }

    private static void test() {
        Object o1 = null;
        for (int i = 0; i < stop; i++) {
            try {
                Object o = new TestSubTypeCheckMacroNodeWrongMem();
                o1.equals(o);
            } catch (NullPointerException npe) {
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }
}

/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6487370
 * @author Maurizio Cimadamore
 * @summary javac incorrectly gives ambiguity warning with override-equivalent abstract inherited methods
 */

public class T6487370 {

    interface I1 {
        String m(Number n);
    }

    interface I2 {
        Object m(Number n);
    }

    static abstract class X implements I1, I2 {
        String test() {
            return m(0.0f);
        }
    }

    static class W extends X {
        public String m(Number n) {
            return "Hello!";
        }
    }

    public static void main(String args[]) {
        System.out.println(new W().test());
    }
}

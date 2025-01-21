/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4848018
 * @summary REGRESSION: NullPointerException in Flow.visitTry(Flow.java:873)
 * @author gafter
 *
 * @compile DALoop1.java
 */

class DALoop1 {

    String className() {
        do {
            try {
                Class.forName("");
            } catch (ClassNotFoundException e) {}
        } while (true);
    }

    static class QualName {
        public final int X;
        QualName() {
            throw new Error();
        }
    }
}

/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8058511
 * @summary StackOverflowError at com.sun.tools.javac.code.Types.lub
 * @compile T8058511c.java
 */
import java.util.List;

class T8058511c {
    void test(List<? extends double[]> l) {
        (true ? l.get(0) : l.get(0)).toString();
    }
}

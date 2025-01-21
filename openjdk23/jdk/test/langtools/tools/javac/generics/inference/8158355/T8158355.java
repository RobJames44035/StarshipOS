/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8158355
 * @summary Inference graph dot support broken
 * @compile --debug=dumpInferenceGraphsTo=. T8158355.java
 */
import java.util.List;

class T8158355 {
    <Z> List<Z> m() { return null; }

    void test() {
        List<String> ls = m();
    }
}

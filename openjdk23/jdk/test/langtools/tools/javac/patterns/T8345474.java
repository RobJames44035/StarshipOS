/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8345474
 * @summary Translation for instanceof is not triggered when patterns are not used in the compilation unit
 * @enablePreview
 * @compile T8345474.java
 * @run main T8345474
 */
import java.util.List;

public class T8345474 {
    public static void main(String[] args) {
        erasureInstanceofTypeComparisonOperator();
    }

    public static void erasureInstanceofTypeComparisonOperator() {
        List<Short> ls = List.of((short) 42);

        assertTrue(ls.get(0) instanceof int);
    }

    static void assertTrue(boolean actual) {
        if (!actual) {
            throw new AssertionError("Expected: true, but got false");
        }
    }
}

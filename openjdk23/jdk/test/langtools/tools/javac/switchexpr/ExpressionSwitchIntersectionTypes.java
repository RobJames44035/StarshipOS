/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8206986
 * @summary Verify behavior when an intersection type is inferred for switch expression.
 * @compile ExpressionSwitchIntersectionTypes.java
 * @run main ExpressionSwitchIntersectionTypes
 */

public class ExpressionSwitchIntersectionTypes<X  extends java.io.Serializable & Runnable> {

    void test1(int i, X x) {
        Runnable r1 = switch (i) {
            default -> x;
        };
        r1.run();
    }

    void test2(int i, X x) {
        (switch (i) {
            default -> x;
        }).run();
    }

    public static void main(String[] args) {
        ExpressionSwitchIntersectionTypes t = new ExpressionSwitchIntersectionTypes();
        try {
            t.test1(0, "");
            throw new AssertionError("Expected exception didn't occur.");
        } catch (ClassCastException ex) {
            //good
        }
        try {
            t.test2(0, "");
            throw new AssertionError("Expected exception didn't occur.");
        } catch (ClassCastException ex) {
            //good
        }
    }

}

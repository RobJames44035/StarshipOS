/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8206986
 * @summary Verify rule cases with expression statements and throw statements work.
 * @compile BlockExpression.java
 * @run main BlockExpression
 */

public class BlockExpression {

    public static void main(String... args) {
        T t = T.B;

        try {
            int ii = switch (t) {
                case A -> 0;
                default -> throw new IllegalStateException();
            };
            throw new AssertionError("Expected exception not thrown.");
        } catch (IllegalStateException ex) {
            //OK
        }
    }

    enum T {
        A, B;
    }

}

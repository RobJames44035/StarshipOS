/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8077667
 * @summary Eliminate bogus error about lambda parameter not being initialized.
 * @compile LambdaParameterNeedsNoInitTest.java
 */
import java.util.function.Predicate;

public class LambdaParameterNeedsNoInitTest {

    public static void main(String[] args) {
        new Inner();
    }

    private static class Inner {
        Predicate<String> synonymComparator = a -> a.isEmpty();
        Inner() {
            if (true) {
                return;
            }
            synonymComparator.test("");
        }
    }
}

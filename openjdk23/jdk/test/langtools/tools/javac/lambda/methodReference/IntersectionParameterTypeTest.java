/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8292975
 * @summary Javac produces code that crashes with LambdaConversionException
 * @run main IntersectionParameterTypeTest
 */

import java.util.function.BiFunction;

public class IntersectionParameterTypeTest {

    sealed interface Term {
        record Lit() implements Term {}
        record Lam(String x, Term a) implements Term {}
    }

    public static <U, T> void call(BiFunction<U, T, T> op, U x, T t) {
      op.apply(x, t);
    }

    public static void main(String[] args) {
      // this code works
      call(Term.Lam::new, "x", (Term) new Term.Lit());

      // this does not
      call(Term.Lam::new, "x", new Term.Lit());
  }
}

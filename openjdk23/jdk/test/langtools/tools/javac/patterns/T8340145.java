/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8340145
 * @summary Problem with generic pattern matching results in internal compiler error
 * @compile T8340145.java
 * @run main T8340145
 */
public class T8340145 {
    public static void main(String[] args) {
        Option<Integer> optionInteger = new Option.Some<>(21);
        Number number = Option.unwrapOrElse(optionInteger, 5.2);

        Option2<Impl> optionBound = new Option2.Some<>(new Impl (){});
        Bound number2 = Option2.unwrapOrElse(optionBound, new Impl(){});
    }

    sealed interface Option<T> permits Option.Some, Option.None {
        record Some<T>(T value) implements Option<T> {}
        record None<T>() implements Option<T> {}

        static <T, T2 extends T> T unwrapOrElse(Option<T2> option, T defaultValue) {
            return switch (option) {
                case Option.Some(T2 value) -> value;
                case Option.None<T2> _ -> defaultValue;
            };
        }
    }

    interface Bound {}
    interface Bound2 {}
    static class Impl implements Bound, Bound2 {}
    sealed interface Option2<T> permits Option2.Some, Option2.None {
        record Some<T>(T value) implements Option2<T> {}
        record None<T>() implements Option2<T> {}

        static <T extends Bound & Bound2> T unwrapOrElse(Option2<T> option, T defaultValue) {
            return switch (option) {
                case Option2.Some(T value) -> value;
                case Option2.None<T> _ -> defaultValue;
            };
        }
    }
}

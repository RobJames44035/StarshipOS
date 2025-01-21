/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class T7034495 {

    interface A<T> {
        T foo();
    }

    interface B<T> {
        T foo();
    }

    interface C<T extends A<?> & B<?>> { }

}

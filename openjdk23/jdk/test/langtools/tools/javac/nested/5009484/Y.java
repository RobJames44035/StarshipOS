/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class Y<T> {
    private T t;
    class Foo extends Y<Y<T>> {
        Y<T> y = t;
    }
}

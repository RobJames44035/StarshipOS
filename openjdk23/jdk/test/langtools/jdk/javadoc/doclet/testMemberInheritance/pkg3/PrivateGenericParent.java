/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package pkg3;

public class PrivateGenericParent {

    private static class PrivateParent<T> {
        public T method(T t) {
            return t;
        }
    }

    public class PublicChild extends PrivateParent<String> {}
}

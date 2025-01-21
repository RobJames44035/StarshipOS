/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class T6295056 {
    interface Foo {}
    interface Bar<X> {}

    Object m(Foo f) {
        return (Bar<Object>)f;
    }

}

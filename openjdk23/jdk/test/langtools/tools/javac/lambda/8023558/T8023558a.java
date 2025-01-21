/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8023558
 * @summary Javac creates invalid bootstrap methods for complex lambda/methodref case
 */
public class T8023558a {
    interface SAM<T> {
        T get();
    }

    static class K<T> implements SAM<T> {
        public T get() {
            return (T)this;
        }
    }

    public static void main(String[] args) {
        SAM<SAM> sam = new SAM<SAM>() { public SAM get() { return new K<>(); } };
        SAM temp = sam.get()::get;
    }
}

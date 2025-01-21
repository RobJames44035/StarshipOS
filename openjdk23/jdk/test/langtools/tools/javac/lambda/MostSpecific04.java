/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class MostSpecific04 {

    interface DoubleMapper<T> {
        double map(T t);
    }

    interface LongMapper<T> {
        long map(T t);
    }

    static class MyList<E> {
        void map(DoubleMapper<? super E> m) { }
        void map(LongMapper<? super E> m) { }
    }

    public static void meth() {
        MyList<String> ls = new MyList<String>();
        ls.map(e->e.length()); //ambiguous - implicit
        ls.map((String e)->e.length()); //ok
    }
}

/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class T6450290 {
    static class Box<X extends Box<?,?>, T extends X> {
        T value;
        Box<X, T> same;
    }

    static class A extends Box<A,A> {}
    static class B extends Box<B,B> {}
    public static void meth() {
        Box<?,?> b = new Box<Box<A,A>,Box<A,A>>();
        b.value.same = new Box<B,B>(); //javac misses this bad assignment
    }
}

/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6390045
 * @summary Unexpected error "cannot access java.lang.Void" with '-target cldc1.0' with -source >=1.5
 *
 * @author mcimadamore
 * @compile -XDfailcomplete=java.lang.Void T6390045b.java
 */

class T6390045b {
    short s;
    Object o;
    Object p = choose(o, s);
    <T> T choose(T t1, T t2) { return t1; }
}

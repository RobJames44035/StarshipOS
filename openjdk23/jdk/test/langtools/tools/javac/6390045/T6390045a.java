/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6390045
 * @summary Unexpected error "cannot access java.lang.Void" with '-target cldc1.0' with -source >=1.5
 *
 * @author mcimadamore
 * @compile -XDfailcomplete=java.lang.Void T6390045a.java
 */

class T6390045a {
    boolean b;
    short s;
    Object o;
    Object p = b ? o : s;
}

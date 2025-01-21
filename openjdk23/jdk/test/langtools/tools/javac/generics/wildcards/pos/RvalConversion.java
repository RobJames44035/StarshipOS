/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4918637 4931781
 * @summary rvalue conversion changes "? extends X" to "X".
 * @author gafter
 *
 * @compile  RvalConversion.java
 */

import java.util.*;

class T {
    void f(Map<?,?> m) {
        for ( Map.Entry e : m.entrySet() ) { }
    }
}
class Box<T> {
    T get() { return null; }
}
class Main {
    public static void main(String[] args) {
        Box<? extends Integer> bi = null;
        int i = bi.get();
    }
}

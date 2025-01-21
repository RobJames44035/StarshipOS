/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8056014
 * @summary Verify that full type inference is used when calling a method on a type variable.
 * @compile T8056014.java
 * @run main T8056014
 */

import java.util.*;

public class T8056014 {
    public static void main(String[] args) {
        new T8056014().run();
    }

    void run() {
        List<S> l = Arrays.asList(new S());
        C<S> c = new C<>(new S());
        foo(l.get(0).copy(1));
        foo(c.get(0).copy(1));
    }

    void foo(S d) {
    }
}

class B {
    public B copy(long j) {
        throw new AssertionError("Should not get here.");
    }
}

class S extends B {
    public <T> T copy(int i) {
        return null;
    }
}

class C<T extends B> {
    final T t;
    public C(T t) {
        this.t = t;
    }
    public T get(int i) {
        return t;
    }
}

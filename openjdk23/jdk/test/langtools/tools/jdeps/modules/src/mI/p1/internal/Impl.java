/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p1.internal;

import p1.S;

public class Impl implements S {
    public Impl() {
        q.Counter.create("impl.counter");
    }

    public String name() {
        return Impl.class.getName();
    }
}

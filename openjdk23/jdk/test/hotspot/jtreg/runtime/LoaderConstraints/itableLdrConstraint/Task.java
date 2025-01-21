/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package test;

public class Task implements Runnable {

    public void run() {
        Class<?> c = Foo.class; // forces PreemptingClassLoader to load Foo
        C x = new C(); // triggers overloading constraints
        x.m();
    }
}

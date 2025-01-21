/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8144066
 * @summary GLB of two lower-bounded capture variables, bounded by related array types
 * @compile CaptureGLB1.java
 */

public class CaptureGLB1 {

    interface A<T> { }

    Exception[] bar(A<? super Exception[]> x, A<? super Throwable[]> y){
        return foo(x, y);
    }

    <T> T foo(A<? super T> x, A<? super T> y){
        return null;
    }
}

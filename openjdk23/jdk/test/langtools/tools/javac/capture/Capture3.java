/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4916563
 * @summary new wildcard subst scheme breaks java.lang.ref
 * @author gafter
 *
 * @compile Capture3.java
 */

package capture3;

class Q<T> {
    void enqueue(Ref<? extends T> r) {
    }
}

class Ref<T> {
    Q<? super T> queue;

    void enqueue() {
        this.queue.enqueue(this);
    }
}

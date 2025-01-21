/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// key: compiler.warn.attempt.to.synchronize.on.instance.of.value.based.class
// options: -Xlint:synchronization

class AttemptToSynchronizeOnInstanceOfVbc {
    void foo(Integer i) {
        synchronized(i) {
        }
    }
}

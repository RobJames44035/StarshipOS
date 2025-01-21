/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class VoidCompatibility {

    interface Runnable { void run(); } //1
    interface Thunk<T> { T get(); } //2

    void schedule(Runnable r) { }
    void schedule(Thunk<?> t) { }

    void test() {
        schedule(() -> System.setProperty("done", "true")); //non-void most specific
        schedule(() -> { System.setProperty("done", "true"); }); //1
        schedule(() -> { return System.setProperty("done", "true"); }); //2
        schedule(() -> System.out.println("done")); //1
        schedule(() -> { System.out.println("done"); }); //1
        schedule(Thread::yield); //1
        schedule(Thread::getAllStackTraces); //non-void most specific
        schedule(Thread::interrupted); //1 (most specific)
    }
}

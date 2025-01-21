/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8164399
 * @summary inference of thrown variable does not work correctly
 * @compile T8164399.java
 */

abstract class T8164399 {

    interface ThrowableRunnable<E extends Throwable> {
       void compute() throws E;
    }

    public abstract < E extends Exception> void computeException(ThrowableRunnable<E> process) throws E;


    public static <T, E extends Throwable> T compute(ThrowableRunnable<E> action) throws E {
        return null;
    }

    {
        computeException(() -> compute(() -> {}));
    }
}

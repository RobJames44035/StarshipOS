/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 *
 */

package bench;

/**
 * Interface that each benchmark must implement.
 */
public interface Benchmark {
    /**
     * Run the benchmark.  Return length of time (in milliseconds) that run
     * took.
     */
    long run(String[] args) throws Exception;
}

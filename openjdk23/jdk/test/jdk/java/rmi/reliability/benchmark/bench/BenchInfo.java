/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 *
 */

package bench;

/**
 * Information about a benchmark: its name, how long it took to run, and the
 * weight associated with it (for calculating the overall score).
 */
public class BenchInfo {

    Benchmark benchmark;
    String name;
    long time;
    float weight;
    String[] args;

    /**
     * Construct benchmark info.
     */
    BenchInfo(Benchmark benchmark, String name, float weight, String[] args) {
        this.benchmark = benchmark;
        this.name = name;
        this.weight = weight;
        this.args = args;
        this.time = -1;
    }

    /**
     * Run benchmark with specified args.  Called only by the harness.
     */
    void runBenchmark() throws Exception {
        time = benchmark.run(args);
    }

    /**
     * Return the benchmark for this benchmark info.
     */
    public Benchmark getBenchmark() {
        return benchmark;
    }

    /**
     * Return the name of this benchmark.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the execution time for benchmark, or -1 if benchmark hasn't been
     * run to completion.
     */
    public long getTime() {
        return time;
    }

    /**
     * Return weight associated with benchmark.
     */
    public float getWeight() {
        return weight;
    }
}

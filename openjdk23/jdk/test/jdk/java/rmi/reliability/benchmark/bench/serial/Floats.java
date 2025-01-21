/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 *
 */

package bench.serial;

import bench.Benchmark;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Benchmark for testing speed of float reads/writes.
 */
public class Floats implements Benchmark {

    /**
     * Write and read float values to/from a stream.  The benchmark is run in
     * batches: each "batch" consists of a fixed number of read/write cycles,
     * and the stream is flushed (and underlying stream buffer cleared) in
     * between each batch.
     * Arguments: <# batches> <# cycles per batch>
     */
    public long run(String[] args) throws Exception {
        int nbatches = Integer.parseInt(args[0]);
        int ncycles = Integer.parseInt(args[1]);
        StreamBuffer sbuf = new StreamBuffer();
        ObjectOutputStream oout =
            new ObjectOutputStream(sbuf.getOutputStream());
        ObjectInputStream oin =
            new ObjectInputStream(sbuf.getInputStream());

        doReps(oout, oin, sbuf, 1, ncycles);    // warmup

        long start = System.currentTimeMillis();
        doReps(oout, oin, sbuf, nbatches, ncycles);
        return System.currentTimeMillis() - start;
    }

    /**
     * Run benchmark for given number of batches, with given number of cycles
     * for each batch.
     */
    void doReps(ObjectOutputStream oout, ObjectInputStream oin,
                StreamBuffer sbuf, int nbatches, int ncycles)
        throws Exception
    {
        for (int i = 0; i < nbatches; i++) {
            sbuf.reset();
            for (int j = 0; j < ncycles; j++) {
                oout.writeFloat((float) 0.0);
            }
            oout.flush();
            for (int j = 0; j < ncycles; j++) {
                oin.readFloat();
            }
        }
    }
}

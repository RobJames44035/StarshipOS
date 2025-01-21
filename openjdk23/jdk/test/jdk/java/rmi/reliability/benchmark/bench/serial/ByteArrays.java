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
 * Benchmark for testing speed of byte array reads/writes.
 */
public class ByteArrays implements Benchmark {

    /**
     * Write and read byte arrays to/from a stream.  The benchmark is run in
     * batches, with each batch consisting of a fixed number of read/write
     * cycles.  The ObjectOutputStream is reset after each batch of cycles has
     * completed.
     * Arguments: <array size> <# batches> <# cycles per batch>
     */
    public long run(String[] args) throws Exception {
        int size = Integer.parseInt(args[0]);
        int nbatches = Integer.parseInt(args[1]);
        int ncycles = Integer.parseInt(args[2]);
        byte[][] arrays = new byte[ncycles][size];
        StreamBuffer sbuf = new StreamBuffer();
        ObjectOutputStream oout =
            new ObjectOutputStream(sbuf.getOutputStream());
        ObjectInputStream oin =
            new ObjectInputStream(sbuf.getInputStream());

        doReps(oout, oin, sbuf, arrays, 1);     // warmup

        long start = System.currentTimeMillis();
        doReps(oout, oin, sbuf, arrays, nbatches);
        return System.currentTimeMillis() - start;
    }

    /**
     * Run benchmark for given number of batches, with given number of cycles
     * for each batch.
     */
    void doReps(ObjectOutputStream oout, ObjectInputStream oin,
                StreamBuffer sbuf, byte[][] arrays, int nbatches)
        throws Exception
    {
        int ncycles = arrays.length;
        for (int i = 0; i < nbatches; i++) {
            sbuf.reset();
            oout.reset();
            for (int j = 0; j < ncycles; j++) {
                oout.writeObject(arrays[j]);
            }
            oout.flush();
            for (int j = 0; j < ncycles; j++) {
                oin.readObject();
            }
        }
    }
}

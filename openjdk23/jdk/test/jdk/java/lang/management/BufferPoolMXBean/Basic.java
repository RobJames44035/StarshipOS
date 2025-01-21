/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
 * @bug 6606598 7024172
 * @summary Unit test for java.lang.management.BufferPoolMXBean
 * @modules jdk.management
 * @run main/othervm Basic
 * @key randomness
 */

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.file.Path;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.*;
import java.nio.channels.FileChannel;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.ref.WeakReference;
import java.util.*;

public class Basic {

    // static fields to ensure buffers aren't GC'ed
    static List<ByteBuffer> buffers;
    static MappedByteBuffer mbb;

    // check counters
    static void check(List<BufferPoolMXBean> pools,
                      int minBufferCount,
                      long minTotalCapacity)
    {
        int bufferCount = 0;
        long totalCap = 0;
        long totalMem = 0;
        for (BufferPoolMXBean pool: pools) {
            bufferCount += pool.getCount();
            totalCap += pool.getTotalCapacity();
            totalMem += pool.getMemoryUsed();
        }
        if (bufferCount < minBufferCount)
            throw new RuntimeException("Count less than expected");
        if (totalMem < minTotalCapacity)
            throw new RuntimeException("Memory usage less than expected");
        if (totalCap < minTotalCapacity)
            throw new RuntimeException("Total capacity less than expected");
    }

    public static void main(String[] args) throws Exception {
        Random rand = new Random();

        // allocate a few direct buffers
        int bufferCount = 5 + rand.nextInt(20);
        buffers = new ArrayList<ByteBuffer>(bufferCount);
        long totalCapacity = 0L;
        for (int i=0; i<bufferCount; i++) {
            int cap = 1024 + rand.nextInt(4096);
            buffers.add( ByteBuffer.allocateDirect(cap) );
            totalCapacity += cap;
        }

        // create a mapped buffer
        Path tmpfile = Files.createTempFile("blah", null);
        tmpfile.toFile().deleteOnExit();
        try (FileChannel fc = FileChannel.open(tmpfile, READ, WRITE)) {
            mbb = fc.map(FileChannel.MapMode.READ_WRITE, 10, 100);
            bufferCount++;
            totalCapacity += mbb.capacity();
        }

        // use platform MXBeans directly
        List<BufferPoolMXBean> pools =
            ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
        check(pools, bufferCount, totalCapacity);

        // use MBeanServer
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> mbeans = server.queryNames(
            new ObjectName("java.nio:type=BufferPool,*"), null);
        pools = new ArrayList<BufferPoolMXBean>();
        for (ObjectName name: mbeans) {
            BufferPoolMXBean pool = ManagementFactory
                .newPlatformMXBeanProxy(server, name.toString(), BufferPoolMXBean.class);
            pools.add(pool);
        }
        check(pools, bufferCount, totalCapacity);

        // attempt to unmap mapped buffer
        WeakReference<MappedByteBuffer> ref = new WeakReference<>(mbb);
        mbb = null;
        do {
            System.gc();
            Thread.sleep(250);
        } while (ref.get() != null);
    }
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.management.jfr;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.Deque;

import jdk.management.jfr.DiskRepository.DiskChunk;
import jdk.jfr.internal.management.HiddenWait;

final class FileDump {
    private final HiddenWait lock = new HiddenWait();
    private final Deque<DiskChunk> chunks = new ArrayDeque<>();
    private final long stopTimeMillis;
    private boolean complete;

    FileDump(long stopTimeMillis) {
        this.stopTimeMillis = stopTimeMillis;
    }

    public void add(DiskChunk dc) {
        synchronized (lock) {
            if (isComplete()) {
                return;
            }
            dc.acquire();
            chunks.addFirst(dc);
            long endMillis = dc.endTimeNanos / 1_000_000;
            if (endMillis >= stopTimeMillis) {
                setComplete();
            }
        }
    }

    public boolean isComplete() {
        synchronized (lock) {
            return complete;
        }
    }

    public void setComplete() {
        synchronized (lock) {
            complete = true;
            lock.notifyAll();
        }
    }

    public void close() {
        synchronized (lock) {
            for (DiskChunk dc : chunks) {
                dc.release();
            }
            chunks.clear();
            complete = true;
        }
    }

    private DiskChunk oldestChunk() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                if (!chunks.isEmpty()) {
                    return chunks.pollLast();
                }
                if (complete) {
                    return null;
                }
                lock.wait();
            }
        }
    }

    public void write(Path path) throws IOException, InterruptedException {
        DiskChunk chunk = null;
        try (FileChannel out = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            while ((chunk = oldestChunk()) != null) {
                try (FileChannel in = FileChannel.open(chunk.path(), StandardOpenOption.READ)) {
                    in.transferTo(0, in.size(), out);
                }
                chunk.release();
                chunk = null;
            }
        } finally {
            if (chunk != null) {
                chunk.release();
            }
            close();
        }
    }
}
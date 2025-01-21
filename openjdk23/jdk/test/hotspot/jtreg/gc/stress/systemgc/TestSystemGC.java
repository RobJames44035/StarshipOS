/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.stress.systemgc;

// A test that stresses a full GC by allocating objects of different lifetimes
// and concurrently calling System.gc().

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

final class ThreadUtils {
    public static void sleep(long durationMS) {
        try {
            Thread.sleep(durationMS);
        } catch (Exception e) {
        }
    }
}

class Exitable {
    private volatile boolean shouldExit = false;

    protected boolean shouldExit() {
        return shouldExit;
    }

    public void exit() {
        shouldExit = true;
    }
}

class ShortLivedAllocationTask extends Exitable implements Runnable {
    private Map<String, String> map = new HashMap<>();

    @Override
    public void run() {
        map = new HashMap<>();
        while (!shouldExit()) {
            for (int i = 0; i < 200; i++) {
                String key = "short" + " key = " + i;
                String value = "the value is " + i;
                map.put(key, value);
            }
        }
    }
}

class LongLivedAllocationTask extends Exitable implements Runnable {
    private Map<String, String> map;

    LongLivedAllocationTask(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        while (!shouldExit()) {
            String prefix = "long" + System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                String key = prefix + " key = " + i;
                String value = "the value is " + i;
                map.put(key, value);
            }
        }
    }
}

class SystemGCTask extends Exitable implements Runnable {
    private long delayMS;

    SystemGCTask(long delayMS) {
        this.delayMS = delayMS;
    }

    @Override
    public void run() {
        while (!shouldExit()) {
            System.gc();
            ThreadUtils.sleep(delayMS);
        }
    }
}

public class TestSystemGC {
    private static long endTimeNanos;

    private static final int numGroups = 7;
    private static final int numGCsPerGroup = 4;

    private static Map<String, String> longLivedMap = new TreeMap<>();

    private static void populateLongLived() {
        for (int i = 0; i < 1000000; i++) {
            String key = "all" + " key = " + i;
            String value = "the value is " + i;
            longLivedMap.put(key, value);
        }
    }

    private static long getDelayMS(int group) {
        if (group == 0) {
            return 0;
        }

        int res = 16;
        for (int i = 0; i < group; i++) {
            res *= 2;
        }
        return res;
    }

    private static void doSystemGCs() {
        ThreadUtils.sleep(1000);

        for (int i = 0; i < numGroups; i++) {
            for (int j = 0; j < numGCsPerGroup; j++) {
               System.gc();
               if (System.nanoTime() - endTimeNanos >= 0) {
                   return;
               }
               ThreadUtils.sleep(getDelayMS(i));
            }
        }
    }

    private static SystemGCTask createSystemGCTask(int group) {
        long delay0 = getDelayMS(group);
        long delay1 = getDelayMS(group + 1);
        long delay = delay0 + (delay1 - delay0) / 2;
        return new SystemGCTask(delay);
    }

    private static void startTask(Runnable task) {
        if (task != null) {
            new Thread(task).start();
        }
    }

    private static void exitTask(Exitable task) {
        if (task != null) {
            task.exit();
        }
    }

    private static void runAllPhases() {
        for (int i = 0; i < 4 && System.nanoTime() - endTimeNanos < 0; i++) {
            SystemGCTask gcTask =
                (i % 2 == 1) ? createSystemGCTask(numGroups / 3) : null;
            ShortLivedAllocationTask shortTask =
                (i == 1 || i == 3) ?  new ShortLivedAllocationTask() : null;
            LongLivedAllocationTask longTask =
                (i == 2 || i == 3) ? new LongLivedAllocationTask(longLivedMap) : null;

            startTask(gcTask);
            startTask(shortTask);
            startTask(longTask);

            doSystemGCs();

            exitTask(gcTask);
            exitTask(shortTask);
            exitTask(longTask);

            ThreadUtils.sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Must specify timeout in seconds as first argument");
        }
        long timeoutNanos = Integer.parseInt(args[0]) * 1_000_000_000L;
        System.out.println("Running with timeout of " + args[0] + " seconds");
        endTimeNanos = System.nanoTime() + timeoutNanos;
        // First allocate the long lived objects and then run all phases.
        populateLongLived();
        runAllPhases();
    }
}

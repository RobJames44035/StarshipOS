/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/StringInternSyncWithGC.
 * VM Testbase keywords: [gc, stress, stressopt, feature_perm_removal_jdk7, nonconcurrent]
 * VM Testbase readme:
 * The test verifies that String.intern is correctly synchronized with GC.
 * Test interns and drop the same strings in different threads and provokes GC.
 * Additionally test creates weak/soft references to interned strings.
 * Test fails if any string object is inaccessible.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm
 *      -Xlog:gc:gc.log
 *      gc.gctests.StringInternSyncWithGC.StringInternSyncWithGC
 *      -ms low
 *      -memUsage 3
 *      -appTimeout 30
 *      -capacityVerPart 2
 */

package gc.gctests.StringInternSyncWithGC;

import java.util.ArrayList;
import java.util.List;

import nsk.share.gc.*;
import nsk.share.gc.gp.MemoryStrategy;
import nsk.share.gc.gp.MemoryStrategyAware;
import nsk.share.gc.gp.string.RandomStringProducer;
import nsk.share.test.ExecutionController;

public class StringInternSyncWithGC extends ThreadedGCTest implements MemoryStrategyAware {

    // Maximum size of one string
    // Depends from all size and memory strategy
    private int maxStringSize;
    private MemoryStrategy memoryStrategy;
    private final int memUsageFactor;
    private final long endTimeCapacityVer;

    // The list of strings which are interned during iteration
    private final List<String> stringsToIntern = new ArrayList();
    private final RandomStringProducer gp = new RandomStringProducer();

    public StringInternSyncWithGC(int memUsage, long endTimeCapVer) {
        memUsageFactor = memUsage;
        endTimeCapacityVer = endTimeCapVer;
    }

    @Override
    public void setMemoryStrategy(MemoryStrategy memoryStrategy) {
        this.memoryStrategy = memoryStrategy;
    }

    /**
     * Verify that we could use certain amount of memory.
     */
    private boolean verifyInternedStringCapacity(long initialSize) {
        long currentSize = 0;
        final int STEP = 1000;
        int iter = 0;
        char[] template = new char[(int) (initialSize / STEP)];

        List<String> tmpList = new ArrayList<>(STEP);
        try {
            while (currentSize <= initialSize) {
                if (endTimeCapacityVer < System.currentTimeMillis()) {
                    log.debug("Too long to verify interned string capacity");
                    log.debug("Silently pass.");
                    return false;
                }
                template[iter]++;
                if (++iter == template.length) {
                    iter = 0;
                }
                String str = new String(template);
                tmpList.add(str.intern());
                currentSize += str.length() * 2; //each char costs 2 bytes
            }
        } catch (OutOfMemoryError oome) {
            log.debug("It is not possible to allocate " + initialSize + " size of interned string.");
            log.debug("Silently pass.");
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        long size = runParams.getTestMemory() / memUsageFactor;
        if (!verifyInternedStringCapacity(size)) {
            return;
        }
        // Approximate size occupied by all interned strings
        long sizeOfAllInternedStrings = size / 2;
        maxStringSize = (int) (sizeOfAllInternedStrings / memoryStrategy.getSize(sizeOfAllInternedStrings, Memory.getObjectExtraSize()));
        // Each thread keeps reference to each created string.
        long extraConsumption = runParams.getNumberOfThreads() * Memory.getReferenceSize();
        log.debug("The overall size of interned strings  : " + sizeOfAllInternedStrings / (1024 * 1024) + "M");
        log.debug("The count of interned strings : " + sizeOfAllInternedStrings / (maxStringSize + extraConsumption));
        for (long currentSize = 0; currentSize <= sizeOfAllInternedStrings;
                currentSize += maxStringSize + extraConsumption) {
            stringsToIntern.add(gp.create(maxStringSize));
        }
        super.run();
    }

    @Override
    protected Runnable createRunnable(int threadId) {
        return new StringGenerator(threadId, this);
    }

    public static void main(String[] args) {
        int appTimeout = -1;
        int memUsageFactor = 1;
        // Part of time that function verifyInternedStringCapacity can take. Time = Application_Timeout / capacityVerTimePart
        double capacityVerPart = 2;
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-memUsage":
                    memUsageFactor = Integer.parseInt(args[i + 1]);
                    break;
                case "-capacityVerPart":
                    capacityVerPart = Double.parseDouble(args[i + 1]);
                    break;
                case "-appTimeout":
                    appTimeout = Integer.parseInt(args[i + 1]);
                    break;
                default:
            }
        }
        if (appTimeout == -1) {
            throw new IllegalArgumentException("Specify -appTimeout.");
        }
        long endTimeCapacityVer = System.currentTimeMillis() + (long) (appTimeout / capacityVerPart * 60000);
        GC.runTest(new StringInternSyncWithGC(memUsageFactor, endTimeCapacityVer), args);
    }

    protected List<String> getStringsToIntern() {
        return stringsToIntern;
    }

    protected int getNumberOfThreads() {
        return runParams.getNumberOfThreads();
    }

    protected RandomStringProducer getGarbageProducer() {
        return gp;
    }

    protected int getMaxStringSize() {
        return maxStringSize;
    }

    protected ExecutionController getExecController() {
        return getExecutionController();
    }
}
